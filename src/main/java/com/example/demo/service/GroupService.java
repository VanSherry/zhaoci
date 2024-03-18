package com.example.demo.service;

import com.example.demo.entity.EmailEntity;
import com.example.demo.entity.GroupEntity;
import com.example.demo.entity.ResultEntity;
import com.example.demo.error.ErrorEnum;
import com.example.demo.exception.BizException;
import com.example.demo.mapper.EmailMapper;
import com.example.demo.mapper.GroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class GroupService {
    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private EmailMapper emailMapper;
    ResultEntity resultEntity = new ResultEntity<>();
    public ResultEntity insertGroup(int userUid, int friendUid, String groupName){
        try {
            GroupEntity groupEntity = new GroupEntity();
            groupEntity.setGroupName(groupName);
            groupEntity.setFriendUid(friendUid);
            groupEntity.setUserUid(userUid);
            //设置群组内容
            groupMapper.insert(groupEntity);
            //插入群组
            return resultEntity.construct("添加小组成功", true);
        }catch (Exception e){
            throw new BizException(ErrorEnum.DATA_ERROR.getCode(),"添加小组错误");
        }
    }

    public ResultEntity sendEmailToAll(int userUid,String groupName,String title,String content) {
        EmailEntity emailEntity = new EmailEntity();
        Date data = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        //获取发件时间并转化成年月日时格式
        try {
            List<Integer> list = groupMapper.sendEmailToAll(userUid, groupName);
            //将群组中的好友id储存在列表中
            for (int i = 0; i < list.size(); i++) {
                emailEntity.setSenderUid(userUid);
                emailEntity.setReceiverUid(list.get(i));
                emailEntity.setTitle(title);
                emailEntity.setContent(content);
                emailEntity.setSendTime(formatter.format(data));
                emailMapper.insert(emailEntity);
                //按照储存的id逐一发送
            }

            return resultEntity.construct("发送成功", true);
        }catch (Exception e){
            throw new BizException(ErrorEnum.DATA_ERROR.getCode(),"对组员群发错误");
        }
    }
}
