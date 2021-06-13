package com.gustavo.dao;

import com.github.pagehelper.Page;
import com.gustavo.pojo.CheckGroup;

import java.util.Map;

public interface CheckGroupDao {
    public void add(CheckGroup checkGroup);
    public void setCheckGroupAndCheckItem(Map map);

    public Page<CheckGroup> findByCondition(String queryString);
}
