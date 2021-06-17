package com.gustavo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.gustavo.constant.MessageConstant;
import com.gustavo.entity.Result;
import com.gustavo.pojo.Setmeal;
import com.gustavo.service.SetmealService;
import com.gustavo.utils.PmsUploadUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    private SetmealService setmealService;


    //文件上传
    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile){
        try {
            String imgUrl = PmsUploadUtil.uploadImage(imgFile);
            System.out.println(imgUrl);
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,imgUrl );
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal,int[] checkgroupIds){
        try {
            setmealService.add(setmeal,checkgroupIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }

        return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
    }


}
