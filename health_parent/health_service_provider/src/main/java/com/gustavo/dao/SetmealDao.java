package com.gustavo.dao;

import com.github.pagehelper.Page;
import com.gustavo.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetmealDao {

    public void add(Setmeal setmeal);

    void setSetmealAndCheckGroup(Map<String, Integer> map);

    public Page<Setmeal> findByCondition(String queryString);

    public List<Setmeal> findAll();

    public Long findCountByCheckgroupId(Integer id);

    public void deleteById(Integer id);

    public Setmeal findById(Integer id);

    public List<Integer> findCheckgroupIdsBySetmealId(Integer id);

    public void edit(Setmeal setmeal);

    public void deleteAssocication(Integer id);

    public List<Map<String, Object>> findSetmealCount();
}
