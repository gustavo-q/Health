package com.gustavo.service;

import com.gustavo.entity.PageResult;
import com.gustavo.entity.QueryPageBean;
import com.gustavo.pojo.CheckGroup;

import java.util.Map;

public interface CheckGroupService {
    public void add(CheckGroup checkGroup, Integer[] checkitemIds);

    public PageResult pageQuery(QueryPageBean queryPageBean);
}
