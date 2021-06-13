package com.gustavo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.gustavo.service.CheckItemService;
import com.gustavo.dao.CheckItemDao;
import com.gustavo.entity.PageResult;
import com.gustavo.entity.QueryPageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.gustavo.pojo.CheckItem;

import java.util.List;

/**
 * 检查项服务
 */
@Service(interfaceClass = CheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements CheckItemService {
    //注入DAO对象
    @Autowired
    private CheckItemDao checkItemDao;

    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }


    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();//查询条件
        //完成分页查询，基于mybatis框架提供的分页助手插件完成
        PageHelper.startPage(currentPage,pageSize);
        //select * from t_checkitem limit 0,10
        Page<CheckItem> page = checkItemDao.selectByCondition(queryString);
        long total = page.getTotal();
        List<CheckItem> rows = page.getResult();
        return new PageResult(total,rows);
    }


    //根据ID删除检查项
    public void deleteById(Integer id) {
        //判断当前检查项是否关联检查组
        Long count = checkItemDao.findCountByCheckItemId(id);
        if (count >0){
            //当前检查项已经关联到检查组，不允许删除
            new RuntimeException();
        }
        checkItemDao.deleteById(id);

    }

    @Override
    public CheckItem findById(Integer id) {
        return checkItemDao.findById(id);

    }

    @Override
    public void edit(CheckItem checkItem) {
        checkItemDao.edit(checkItem);
    }

    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }
}
