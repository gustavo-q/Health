package com.gustavo.dao;

import com.gustavo.pojo.Order;

import java.util.List;

public interface OrderDao {
    public List<Order> findByCondition(Order order) ;

    public void add(Order order);
}
