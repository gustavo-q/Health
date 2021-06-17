package com.gustavo.service.impl;

import com.gustavo.dao.SetmealDao;
import com.gustavo.pojo.Setmeal;
import com.gustavo.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;

    //添加套餐
    @Override
    public void add(Setmeal setmeal, int[] checkgroupIds) {
        setmealDao.add(setmeal);
        Integer setmealId = setmeal.getId();
        this.setSetmealAndCheckgroup(setmealId,checkgroupIds);
        //将图片名称保存到Redis集合中
        String filename = setmeal.getImg();

    }

    private void setSetmealAndCheckgroup(Integer setmealId, int[] checkgroupIds) {
        if(checkgroupIds != null && checkgroupIds.length > 0){
            for (Integer checkgroupId : checkgroupIds) {
                Map<String,Integer> map = new HashMap<>();
                map.put("setmealId",setmealId);
                map.put("checkgroupId",checkgroupId);
                setmealDao.setSetmealAndCheckGroup(map);
            }
        }

    }
}
