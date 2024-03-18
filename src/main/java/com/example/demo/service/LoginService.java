package com.example.demo.service;

import com.example.demo.entity.ResultEntity;
import com.example.demo.error.ErrorEnum;
import com.example.demo.exception.BizException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.utils.JwtUtils;
import com.example.demo.utils.ToolUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {
    @Autowired
    private UserMapper userMapper;
    ResultEntity resultEntity = new ResultEntity<>();
    public ResultEntity saveUser(String userName, String password){//注册
        //密码限制
        if(password.length()<6)
        {
            return resultEntity.construct("密码长度不得小于6位",false);
        }
        //验证用户名密码是否重复
        if(userMapper.loginByUserName(userName, ToolUtils.MD5(password))){
            return resultEntity.construct("用户名密码重复",false);
        }

        //获取请求参数并加密保存
        int temp = userMapper.insertUser(userName,ToolUtils.MD5(password));
        //返回uid
        try{
            return resultEntity.construct("uid:",true,userMapper.getUid(ToolUtils.MD5(password),userName));
        }catch (Exception e){
            throw new BizException(ErrorEnum.DATA_ERROR.getCode(),"密码加密错误");
        }
    }

    public ResultEntity login(String userName,String password,Integer uid){//登录
        password = ToolUtils.MD5(password);
        if(userMapper.loginByUserName(userName, password)) {
            //用户名密码登录
            //生成令牌并下发
            Map<String,Object> claims = new HashMap<>();
            claims.put("name",userName);

            String jwt = JwtUtils.generateJwt(claims);
            int userUid = userMapper.getUid(password, userName);

            return resultEntity.construct("用户id为:"+userUid,true,jwt);
        } else if (userMapper.loginByUid(uid, password)) {
            //uid密码登录
            Map<String,Object> claims = new HashMap<>();
            claims.put("uid",uid);

            String jwt = JwtUtils.generateJwt(claims);

            return resultEntity.construct("用户id为:"+uid,true,jwt);
        } else{
            return resultEntity.construct("账号或密码错误",false);
        }
    }
}

