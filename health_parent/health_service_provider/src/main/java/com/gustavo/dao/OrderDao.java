package com.gustavo.dao;

import com.gustavo.pojo.Order;

import java.util.List;
import java.util.Map;

public interface OrderDao {
    public List<Order> findByCondition(Order order) ;

    public void add(Order order);

    public Map findById4Detail(Integer id);
}
