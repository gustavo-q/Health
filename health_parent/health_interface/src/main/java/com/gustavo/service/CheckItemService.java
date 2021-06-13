package com.gustavo.service;

import com.gustavo.entity.PageResult;
import com.gustavo.entity.QueryPageBean;
import com.gustavo.pojo.CheckItem;

import java.util.List;

//服务接口
public interface CheckItemService {

    public void add(CheckItem checkItem);

    PageResult pageQuery(QueryPageBean queryPageBean);

    public void deleteById(Integer id);

    public CheckItem findById(Integer id);

    public void edit(CheckItem checkItem);

    public List<CheckItem> findAll();
}
