package com.example.demo.controller;

import com.example.demo.entity.EmailEntity;
import com.example.demo.entity.ResultEntity;
import com.example.demo.service.EmailService;
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
@Api(value = "提供了信件相关的接口",tags = "信件管理")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @RequestMapping(value = "/email/sendEmail",method = RequestMethod.POST)//发送信件
    @ApiOperation("发送信件")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(dataType = "int",name = "senderUid",value = "发信者uid",required = true),
                    @ApiImplicitParam(dataType = "int",name = "receiverUid",value = "收信者uid",required = true),
                    @ApiImplicitParam(dataType = "String",name = "title",value = "信件标题",required = true),
                    @ApiImplicitParam(dataType = "String",name = "content",value = "信件内容",required = true),
            }
    )
    public ResultEntity sendEmail(int senderUid, int receiverUid, String title, String content){
        return emailService.sendEmail(senderUid, receiverUid, title, content);
    }

    @RequestMapping(value = "/email/receiveEmail",method = RequestMethod.GET)//接收信件并分页展示
    @ApiOperation("接收信件并分页展示")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(dataType = "int",name = "receiverUid",value = "收信者uid",required = true),
                    @ApiImplicitParam(dataType = "int",name = "pageNum",value = "接收第几页（一页5条消息）",required = true),
            }
    )
    public ResultEntity receiveEmail(int receiverUid,int pageNum){
        return emailService.receiveEmail(receiverUid,pageNum);
    }

    @RequestMapping(value = "/email/selectEmail",method = RequestMethod.GET)//根据标题和内容模糊查询
    @ApiOperation("根据标题和内容模糊查询")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(dataType = "int",name = "receiverUid",value = "收信者uid",required = true),
                    @ApiImplicitParam(dataType = "String",name = "keyword",value = "查询内容",required = true),
            }
    )
    public ResultEntity selectEmail(int receiverUid,String keyword){
        return emailService.selectEmail(receiverUid, keyword);
    }

    @RequestMapping(value = "/email/driftEmail",method = RequestMethod.POST)//漂流瓶功能
    @ApiOperation("漂流瓶功能")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(dataType = "int",name = "senderUid",value = "发信者uid",required = true),
                    @ApiImplicitParam(dataType = "String",name = "title",value = "信件标题",required = true),
                    @ApiImplicitParam(dataType = "String",name = "content",value = "信件内容",required = true),
            }

    )
    public ResultEntity driftEmail(int senderUid,String title,String content){
        return emailService.driftEmail(senderUid, title, content);
    }

    @RequestMapping(value = "/email/timeEmail",method = RequestMethod.POST)//定时发送
    @ApiOperation("定时发送")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(dataType = "int",name = "senderUid",value = "发信者uid",required = true),
                    @ApiImplicitParam(dataType = "int",name = "receiverUid",value = "收信者uid",required = true),
                    @ApiImplicitParam(dataType = "String",name = "title",value = "信件标题",required = true),
                    @ApiImplicitParam(dataType = "String",name = "content",value = "信件内容",required = true),
                    @ApiImplicitParam(dataType = "int",name = "time",value = "多少分钟后发送",required = true),
            }

    )
    public ResultEntity timeEmail(int senderUid,int receiverUid,String title,String content,int time){
        return emailService.timeEmail(senderUid, receiverUid, title, content, time);
    }

    @RequestMapping(value = "/email/deleteEmail",method = RequestMethod.POST)//删除信件
    @ApiOperation("删除信件")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(dataType = "int",name = "senderUid",value = "发信者uid",required = true),
                    @ApiImplicitParam(dataType = "int",name = "receiverUid",value = "收信者uid",required = true),
                    @ApiImplicitParam(dataType = "String",name = "title",value = "信件标题",required = true),
            }

    )
    public ResultEntity deleteEmail(int senderUid, int receiverUid, String title){
        return emailService.deleteEmail(senderUid, receiverUid, title);
    }
}
