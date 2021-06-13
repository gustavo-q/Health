package com.gustavo.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.gustavo.entity.PageResult;
import com.gustavo.entity.QueryPageBean;
import com.gustavo.service.CheckItemService;
import com.gustavo.constant.MessageConstant;
import com.gustavo.entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.gustavo.pojo.CheckItem;

import java.util.List;

/**
 * 检查项管理
 */

@RestController
@RequestMapping("/checkitem")
public class CheckItemController {

    @Reference
    private CheckItemService checkItemService;

    //新增检查项
    @RequestMapping("/add")
    public Result add(@RequestBody CheckItem checkItem){
        try{
            checkItemService.add(checkItem);
        }catch (Exception e){
            e.printStackTrace();
            //服务调用失败
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
        return  new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
    }


    //检查项分页查询
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = checkItemService.pageQuery(queryPageBean);
        return pageResult;
    }



    //根据id删除检查项
    @RequestMapping("/delete")
    public Result delete(Integer id){
        try {
            checkItemService.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            //服务调用失败
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
        return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }


    //查询单个
    @RequestMapping("/findById")
    public Result findById(Integer id){
        try{
            CheckItem checkItem = checkItemService.findById(id);
            return  new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
        }catch (Exception e){
            e.printStackTrace();
            //服务调用失败
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    //修改检查项
    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckItem checkItem){
        try{
            checkItemService.edit(checkItem);
            return  new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            //服务调用失败
            return new Result(false, MessageConstant.EDIT_CHECKITEM_FAIL);
        }

    }

    //查询所有
    @RequestMapping("/findAll")
    public Result findAll(){
        try{
            List<CheckItem> list = checkItemService.findAll();
        return  new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
        }catch (Exception e){
            e.printStackTrace();
            //服务调用失败
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }






}
