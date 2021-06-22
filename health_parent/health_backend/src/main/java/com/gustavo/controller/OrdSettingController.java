package com.gustavo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.gustavo.constant.MessageConstant;
import com.gustavo.entity.Result;
import com.gustavo.pojo.OrderSetting;
import com.gustavo.service.OrderSettingService;
import com.gustavo.utils.POIUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ordersetting")
public class OrdSettingController {

    @Reference
    private OrderSettingService orderSettingService;

    /**
     * 上传
     * @param excelFile
     * @return
     */
    @RequestMapping("/upload")
    public Result upload(@RequestParam("excelFile")MultipartFile excelFile){
        try {
            List<String[]>  list = POIUtils.readExcel(excelFile);
//            System.out.println(list);
            List<OrderSetting> data = new ArrayList<>();
            for (String[] strings : list){
                String orderDate =  strings[0];
                String number = strings[1];
                System.out.println(orderDate+list);
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                //使用SimpleDateFormat的parse()方法生成Date
                Date da = sf.parse(orderDate);
                OrderSetting orderSetting = new OrderSetting(da,Integer.parseInt(number));
                data.add(orderSetting);
            }
//            System.out.println(data);
            //通过dubbo远程调用服务实现数据批量导入到数据库
            orderSettingService.add(data);
            return new Result(true,MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            //文件解析失败
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }

    }

    /**
     *根据月份查询对应的预约设置数据
     * @param date
     * @return
     */
    @RequestMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String date){
        try {
            List<Map> maps = orderSettingService.getOrderSettingByMonth(date);
//            System.out.println(maps);
            return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,maps);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_ORDERSETTING_FAIL);
        }

    }



    //根据日期设置对应的预约设置数据
    @RequestMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting){
        try{
            orderSettingService.editNumberByDate(orderSetting);
            return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.ORDERSETTING_FAIL);
        }
    }

}
