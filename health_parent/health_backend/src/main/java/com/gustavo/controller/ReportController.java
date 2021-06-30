package com.gustavo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.gustavo.constant.MessageConstant;
import com.gustavo.entity.Result;
import com.gustavo.service.MemberService;
import com.gustavo.service.SetmealService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 报表操作
 */
@RestController
@RequestMapping("/report")
public class ReportController {

    @Reference
    private MemberService memberService;
    @Reference
    private SetmealService setmealService;

    //  会员数量折线图数据
    @RequestMapping("/getMemberReport")
    public Result getMemberReport(){
        //使用模拟数据测试对象格式是转为echarts所需的数据格式
        Map<String,Object> map =new HashMap<>();
        List<String> months = new ArrayList<>();
        Calendar calendar = Calendar.getInstance(); //获取日历对象，当时时间
        calendar.add(Calendar.MONTH,-12); //获得前12个月得时间
        System.out.println(calendar);
        for (int i=0;i<12;i++){
            calendar.add(Calendar.MONTH,1);
            Date date = calendar.getTime();
            months.add(new SimpleDateFormat("yyyy.MM").format(date));
        }
        map.put("months",months);
        List<Integer> memberCount = memberService.findMemberCountByMonths(months);
        map.put("memberCount",memberCount);
        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);
    }

    //套餐预约占比饼图
    @RequestMapping("/getSetmealReport")
    public Result getSetmealReport(){
        //使用模拟数据测试对象格式是转为echarts所需的数据格式
        try {
            Map<String,Object> data =new HashMap<>();
            List<Map<String,Object>> setmealCount = setmealService.findSetmealCount();
            data.put("setmealCount",setmealCount);

            List<String> setmealNames = new ArrayList<>();
            for (Map<String,Object> map : setmealCount){
                String name = (String) map.get("name"); //套餐名称
                setmealNames.add(name);
            }
            data.put("setmealNames",setmealNames);
            return new Result(true, MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS,data);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL);
        }
    }


}
