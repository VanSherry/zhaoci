package com.example.demo.controller;

import com.example.demo.entity.ResultEntity;
import com.example.demo.service.ListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(value = "提供了好友列表相关的接口",tags = "好友管理")
public class ListController {
    @Autowired
    private ListService listService;

    @RequestMapping(value = "/list/add",method = RequestMethod.POST)//添加好友
    @ApiOperation("添加好友")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(dataType = "int",name = "friendUid",value = "好友uid",required = true),
                    @ApiImplicitParam(dataType = "int",name = "userUid",value = "用户uid",required = true),
            }
    )
    public ResultEntity insertFriend(int friendUid, int userUid){
        return listService.insertFriend(friendUid,userUid);
    }

    @RequestMapping(value = "/list/updateRemark",method = RequestMethod.POST)//更改备注
    @ApiOperation("更改备注")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(dataType = "int",name = "userUid",value = "用户uid",required = true),
                    @ApiImplicitParam(dataType = "int",name = "friendUid",value = "好友uid",required = true),
                    @ApiImplicitParam(dataType = "String",name = "remark",value = "备注名称",required = true),
            }
    )
    public ResultEntity updateRemark(int userUid,int friendUid,String remark){
        return listService.updateRemark(userUid, friendUid, remark);
    }

    @RequestMapping(value = "/list/setRelationship",method = RequestMethod.POST)//设置亲密关系
    @ApiOperation("设置亲密关系")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(dataType = "int",name = "userUid",value = "用户uid",required = true),
                    @ApiImplicitParam(dataType = "int",name = "friendUid",value = "好友uid",required = true),
                    @ApiImplicitParam(dataType = "String",name = "relationship",value = "亲密关系名称",required = true),
            }
    )
    public ResultEntity setRelationship(int userUid,int friendUid,String relationship){
        return listService.setRelationship(userUid, friendUid, relationship);
    }

    @RequestMapping(value = "/list/deleteFriend",method = RequestMethod.POST)//假删除好友
    @ApiOperation("假删除好友")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(dataType = "int",name = "userUid",value = "用户uid",required = true),
                    @ApiImplicitParam(dataType = "int",name = "friendUid",value = "好友uid",required = true),
            }
    )
    public ResultEntity deleteFriend(int userUid,int friendUid){
        return listService.deleteFriend(userUid,friendUid);
    }

    @RequestMapping(value = "/list/checkWhoAdd",method = RequestMethod.GET)//查看好友申请
    @ApiOperation("查看好友申请")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(dataType = "int",name = "userUid",value = "用户uid",required = true),
            }
    )
    public ResultEntity checkWhoAdd(int userUid){
        return listService.checkWhoAdd(userUid);
    }

    @RequestMapping(value = "/list/agreeFriendAdd",method = RequestMethod.POST)//同意好友申请
    @ApiOperation("同意好友申请")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(dataType = "int",name = "userUid",value = "用户uid",required = true),
                    @ApiImplicitParam(dataType = "int",name = "friendUid",value = "好友uid",required = true),
            }
    )
    public ResultEntity agreeFriendAdd(int userUid,int friendUid){
        return listService.agreeFriendAdd(userUid,friendUid);
    }

    @RequestMapping(value = "/list/showAllFriend",method = RequestMethod.GET)//查看好友列表
    @ApiOperation("查看好友列表")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(dataType = "int",name = "userUid",value = "用户uid",required = true),
                    @ApiImplicitParam(dataType = "int",name = "pageNum",value = "第几页好友（一页10名好友）",required = true),
            }
    )
    public ResultEntity showAllFriend(int userUid,int pageNum){
        return listService.showAllFriend(userUid,pageNum);
    }
}
