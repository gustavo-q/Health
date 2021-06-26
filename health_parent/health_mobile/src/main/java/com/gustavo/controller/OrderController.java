package com.gustavo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.gustavo.constant.MessageConstant;
import com.gustavo.constant.RedisMessageConstant;
import com.gustavo.entity.Result;
import com.gustavo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * 体检预约处理
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private JedisPool jedisPool;
    @Reference
    private OrderService orderService;

    //在线体检预约
    @RequestMapping("/submit")
    public Result submit(@RequestBody Map map){
        String telephone = (String) map.get("telephone");
        String validateCodeInRedis =jedisPool.getResource().get(telephone+ RedisMessageConstant.SENDTYPE_ORDER);
        String  validateCode = (String) map.get("validateCode");
        //将用户输入的验证码和Redis中保存的验证码进行比对
        if (validateCode!=null && validateCode!=null && validateCode.equals(validateCodeInRedis)){
            //将用户输入的验证码和Redis中保存的验证码进行比对
            Result result = null;
            try {
                result = orderService.order(map);//通过Dubbo远程调用服务实现在线预约业务处理
            } catch (Exception e) {
                e.printStackTrace();
                return result;
            }
         return result;
        }else {
            //如果比对不成功，返回结果给页面
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
    }

    /**
     *
     //根据预约ID查询预约相关信息
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    public Result findById(Integer id){
        try{
            Map map = orderService.findById(id);
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }
    }


}
