package com.gustavo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.gustavo.dao.OrderSettingDao;
import com.gustavo.pojo.OrderSetting;
import com.gustavo.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 预约设置服务
 */
@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    //批量导入预约设置数据
    @Override
    public void add(List<OrderSetting> list) {
        if (list != null && list.size()>0){
            for (OrderSetting orderSetting:list){
                //判断当前日期进行了预约设置
                long countByOrderDate =orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
                if (countByOrderDate > 0){
                    //已经进行了预约设置,执行修改操作
                    orderSettingDao.editNumberByOrderDate(orderSetting);
                }else {
                    ///没有进行预约设置，执行插入操作
                    orderSettingDao.add(orderSetting);
                }
            }
        }
        //判断当前日期是否进行了预约设置

    }
}
