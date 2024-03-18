package com.example.demo.controller;

import com.example.demo.entity.ResultEntity;
import com.example.demo.service.EmailGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "提供了信件群组相关的接口",tags = "信件群组信件管理")
public class EmailGroupController {
    @Autowired
    private EmailGroupService emailGroupService;

    @RequestMapping(value = "/emailGroup/addEmailToGroup",method = RequestMethod.POST)//将邮件添加分组
    @ApiOperation("将邮件添加分组")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(dataType = "int",name = "userUid",value = "发信者uid",required = true),
                    @ApiImplicitParam(dataType = "int",name = "groupName",value = "群组名称",required = true),
                    @ApiImplicitParam(dataType = "String",name = "otherUid",value = "收信者uid",required = true),
                    @ApiImplicitParam(dataType = "String",name = "title",value = "信件标题",required = true),
            }
    )
    public ResultEntity addEmailToGroup(int userUid,String groupName,int otherUid,String title){
        return emailGroupService.addEmailToGroup(userUid, groupName, otherUid, title);
    }

    @RequestMapping(value = "/emailGroup/deletAllGroup",method = RequestMethod.POST)//批量删除
    @ApiOperation("批量删除")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(dataType = "int",name = "userUid",value = "发信者uid",required = true),
                    @ApiImplicitParam(dataType = "int",name = "groupName",value = "群组名称",required = true),
            }
    )
    public ResultEntity deletAllGroup(int userUid,String groupName){
        return emailGroupService.deletAllGroup(userUid, groupName);
    }

    @RequestMapping(value = "/emailGroup/showEmailGroup",method = RequestMethod.GET)//查看群组信件
    @ApiOperation("查看群组信件")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(dataType = "int",name = "userUid",value = "发信者uid",required = true),
                    @ApiImplicitParam(dataType = "int",name = "groupName",value = "群组名称",required = true),
            }
    )
    public ResultEntity showEmailGroup(int userUid,String groupName){
        return emailGroupService.showEmailGroup(userUid, groupName);
    }

    @RequestMapping(value = "/emailGroup/upEmailInGroup",method = RequestMethod.POST)//置顶信件
    @ApiOperation("置顶信件")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(dataType = "int",name = "userUid",value = "发信者uid",required = true),
                    @ApiImplicitParam(dataType = "int",name = "groupName",value = "群组名称",required = true),
                    @ApiImplicitParam(dataType = "String",name = "otherUid",value = "收信者uid",required = true),
                    @ApiImplicitParam(dataType = "String",name = "title",value = "信件标题",required = true),
            }
    )
    public ResultEntity upEmailInGroup(int userUid,String groupName,int otherUid,String title){
        return emailGroupService.upEmailInGroup(userUid, groupName, otherUid, title);
    }
}
