package com.example.demo.controller;

import com.example.demo.entity.ResultEntity;
import com.example.demo.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "提供了登录注册相关的接口",tags = "登录管理")
public class LoginController {
    @Autowired
    private LoginService loginService;

    //接收参数
    @RequestMapping(value = "/login/saveUser",method = RequestMethod.POST)//注册
    @ApiOperation("注册")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(dataType = "String",name = "userName",value = "用户名",required = true),
                    @ApiImplicitParam(dataType = "String",name = "password",value = "密码",required = true),
            }
    )
    public ResultEntity saveUser(String userName, String password){
        //返回uid
        return loginService.saveUser(userName,password);
    }


    @PostMapping(value = "/login/log")//登录
    @ApiOperation("登录")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(dataType = "String",name = "userName",value = "用户名",required = false),
                    @ApiImplicitParam(dataType = "String",name = "password",value = "密码",required = true),
                    @ApiImplicitParam(dataType = "Integer",name = "uid",value = "uid",required = false),
            }
    )
    public ResultEntity login(String userName,String password,Integer uid){
        return loginService.login(userName,password,uid);
    }

}
