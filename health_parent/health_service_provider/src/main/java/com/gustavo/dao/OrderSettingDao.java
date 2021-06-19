package com.gustavo.dao;

import com.gustavo.pojo.OrderSetting;

import java.util.Date;

public interface OrderSettingDao {
    public long findCountByOrderDate(Date orderDate);

    public void editNumberByOrderDate(OrderSetting orderSetting);

    public void add(OrderSetting orderSetting);
}
