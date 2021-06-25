package com.gustavo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.gustavo.constant.RedisConstant;
import com.gustavo.dao.SetmealDao;
import com.gustavo.entity.PageResult;
import com.gustavo.entity.QueryPageBean;
import com.gustavo.pojo.Setmeal;
import com.gustavo.service.CheckGroupService;
import com.gustavo.service.SetmealService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import redis.clients.jedis.JedisPool;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 套餐服务
 */
@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Value("${out_put_path}")
    private String outPutPath; // 从属性文件中读取要生成的html对应的目录

    //添加套餐
    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        setmealDao.add(setmeal);
        Integer setmealId = setmeal.getId();
        this.setSetmealAndCheckgroup(setmealId,checkgroupIds);
        //将图片名称保存到Redis集合中
        savePiv2Redis(setmeal.getImg());

        //当添加套餐后需要重新生成静态页面（套餐列表页面、套餐详情页面）
        generateMobileStaticHtml();

    }


    //生成当前方法所需的静态页面
    private void generateMobileStaticHtml() {
        //在生成静态页面之前需要查询数据
        List<Setmeal> list = setmealDao.findAll();
        //需要生成套餐列表静态页面
        generateMobileSetmealListHtml(list);
        //需要生成套餐详情静态页面
        generateMobileSetmealDetailHtml(list);
    }

    //生成套餐列表静态页面
    private void generateMobileSetmealListHtml(List<Setmeal> list) {
        Map map = new HashMap();
        //为模版提供数据，用于生成静态页面
        map.put("setmealList",list);
        generteHtml("mobile_setmeal.ftl","m_setmeal.html",map);
    }

    //生成套餐详情静态页面（可能有多个）
    private void generateMobileSetmealDetailHtml(List<Setmeal> list) {
        for (Setmeal setmeal:list){
            Map map = new HashMap();
            map.put("setmeal",setmealDao.findById(setmeal.getId()));
            generteHtml("mobile_setmeal_detail.ftl","setmeal_detail_"+setmeal.getId()+".html",map);
        }
    }


    //通用的方法，用于生成静态页面
    public void generteHtml(String templateName,String htmlPgeName,Map map){
        Configuration configuration =freeMarkerConfigurer.getConfiguration(); //获得配置对象
        Writer out = null;
        try {
            Template template = configuration.getTemplate(templateName);
            //构建输出流
             out = new FileWriter(new File(outPutPath)+"/"+htmlPgeName);
            //输出文件
            template.process(map,out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage,pageSize);
        Page<Setmeal> page = setmealDao.findByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public List<Setmeal> findAll() {
        return setmealDao.findAll();
    }

    @Override
    public void delete(Integer id) {
        //判断当前套餐是否关联检查项
        Long count = setmealDao.findCountByCheckgroupId(id);
        if (count >0){
            //当前检查项已经关联到检查项，不允许删除
            new RuntimeException();
        }
        setmealDao.deleteById(id);

    }

    //根据id查询套餐
    @Override
    public Setmeal findById(Integer id) {
        return setmealDao.findById(id);
    }

    @Override
    public List<Integer> findCheckgroupIdsBySetmealId(Integer id) {
       return setmealDao.findCheckgroupIdsBySetmealId(id);
    }

    //编辑套餐
    @Override
    public void edit(Setmeal setmeal, Integer[] checkgroupIds) {
//        修改套餐，操作t_setmeal表
        setmealDao.edit(setmeal);
        //清理当前套餐关联的检查组，操作中间关系表t_setmeal_checkgroup表
        setmealDao.deleteAssocication(setmeal.getId());
        //设置检查组和检查项的多对多的关联关系，操作t_setmeal_checkgroup表
        this.setSetmealAndCheckgroup(setmeal.getId(),checkgroupIds);

    }

    //图片传入redis大集合
    private void savePiv2Redis(String pic) {
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,pic);
    }

    //绑定套餐和检查组
    private void setSetmealAndCheckgroup(Integer setmealId, Integer[] checkgroupIds) {
        if(checkgroupIds != null && checkgroupIds.length > 0){
            for (Integer checkgroupId : checkgroupIds) {
                Map<String,Integer> map = new HashMap<>();
                map.put("setmealId",setmealId);
                map.put("checkgroupId",checkgroupId);
                setmealDao.setSetmealAndCheckGroup(map);
            }
        }

    }
}
