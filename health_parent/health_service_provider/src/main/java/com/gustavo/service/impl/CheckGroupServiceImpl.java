package com.gustavo.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.gustavo.dao.CheckGroupDao;
import com.gustavo.entity.PageResult;
import com.gustavo.entity.QueryPageBean;
import com.gustavo.pojo.CheckGroup;
import com.gustavo.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * 检查组服务
 */
@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;



    //新增检查组，同时需要让检查组关联检查项
    @Override
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        //新增检查组，操作t_checkgroup表
        checkGroupDao.add(checkGroup);
        //设置检查组和检查项的多对多的关联关系，操作t_checkgroup_checkitem表
        Integer checkGroupId = checkGroup.getId();
        this.setCheckGroupAndCheckItem(checkGroupId,checkitemIds);
    }

    //分页
    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage,pageSize);
        Page<CheckGroup> page = checkGroupDao.findByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }


    //建立检查组和检查项多对多关系
    private void setCheckGroupAndCheckItem(Integer checkGroupId, Integer[] checkitemIds) {
        if(checkitemIds != null && checkitemIds.length > 0){
            for (Integer checkitemId : checkitemIds) {
                Map<String,Integer> map = new HashMap<>();
                map.put("checkgroupId",checkGroupId);
                map.put("checkitemId",checkitemId);
                checkGroupDao.setCheckGroupAndCheckItem(map);
            }
        }
    }


}
