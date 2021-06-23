package com.gustavo.service;

import com.gustavo.entity.PageResult;
import com.gustavo.entity.QueryPageBean;
import com.gustavo.pojo.Setmeal;

import java.util.List;

public interface SetmealService {
    public void add(Setmeal setmeal, Integer[] checkgroupIds);

    public PageResult pageQuery(QueryPageBean queryPageBean);

    public List<Setmeal> findAll();

    public void delete(Integer id);

    public Setmeal findById(Integer id);

    public List<Integer> findCheckgroupIdsBySetmealId(Integer id);

    public void edit(Setmeal setmeal, Integer[] checkgroupIds);
}
