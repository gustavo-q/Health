package com.gustavo.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.gustavo.constant.MessageConstant;
import com.gustavo.entity.PageResult;
import com.gustavo.entity.QueryPageBean;
import com.gustavo.entity.Result;
import com.gustavo.pojo.CheckGroup;
import com.gustavo.service.CheckGroupService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 检查组管理
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {

    @Reference
    private CheckGroupService checkGroupService;

    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds){
        try{
            checkGroupService.add(checkGroup,checkitemIds);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);//新增失败
        }
        return new Result(true,MessageConstant.ADD_CHECKGROUP_SUCCESS);//新增成功

    }


    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return checkGroupService.pageQuery(queryPageBean);
    }

}
