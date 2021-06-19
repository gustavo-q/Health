package com.gustavo.service;

import com.gustavo.entity.PageResult;
import com.gustavo.entity.QueryPageBean;
import com.gustavo.pojo.Setmeal;

public interface SetmealService {
    public void add(Setmeal setmeal, int[] checkgroupIds);

    public PageResult pageQuery(QueryPageBean queryPageBean);
}
