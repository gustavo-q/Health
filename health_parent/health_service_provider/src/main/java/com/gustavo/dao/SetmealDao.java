package com.gustavo.dao;

import com.gustavo.pojo.Setmeal;

import java.util.Map;

public interface SetmealDao {

    public void add(Setmeal setmeal);

    void setSetmealAndCheckGroup(Map<String, Integer> map);
}
