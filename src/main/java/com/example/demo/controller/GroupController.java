package com.example.demo.controller;

import com.example.demo.entity.ResultEntity;
import com.example.demo.service.GroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "提供了用户群组相关的接口",tags = "用户群组管理")
public class GroupController {
    @Autowired
    private GroupService groupService;
    @RequestMapping(value = "/group/insertGroup",method = RequestMethod.POST)//创建群组
    @ApiOperation("创建群组")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(dataType = "int",name = "userUid",value = "用户uid",required = true),
                    @ApiImplicitParam(dataType = "int",name = "friendUid",value = "好友uid",required = true),
                    @ApiImplicitParam(dataType = "String",name = "groupName",value = "群组名称",required = true),
            }
    )
    public ResultEntity insertGroup(int userUid, int friendUid, String groupName){
        return groupService.insertGroup(userUid,friendUid,groupName);
    }

    @RequestMapping(value = "/group/sendEmailToAll",method = RequestMethod.POST)//群发消息
    @ApiOperation("群发消息")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(dataType = "int",name = "userUid",value = "用户uid",required = true),
                    @ApiImplicitParam(dataType = "String",name = "groupName",value = "群组名称",required = true),
                    @ApiImplicitParam(dataType = "String",name = "title",value = "信件标题",required = true),
                    @ApiImplicitParam(dataType = "String",name = "content",value = "信件内容",required = true),
            }
    )
    public ResultEntity sendEmailToAll(int userUid,String groupName,String title,String content){
        return groupService.sendEmailToAll(userUid,groupName,title,content);
    }
}
