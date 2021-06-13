package com.gustavo.dao;


import com.github.pagehelper.Page;
import com.gustavo.pojo.CheckItem;


public interface CheckItemDao {

    public void add(CheckItem checkItem);

    Page<CheckItem> selectByCondition(String queryString);
}
