package com.example.demo.service;

import com.example.demo.entity.EmailEntity;
import com.example.demo.entity.EmailGroupEntity;
import com.example.demo.entity.ResultEntity;
import com.example.demo.error.ErrorEnum;
import com.example.demo.exception.BizException;
import com.example.demo.mapper.EmailGroupMapper;
import com.example.demo.mapper.EmailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmailGroupService {
    @Autowired
    private EmailGroupMapper emailGroupMapper;
    @Autowired
    private EmailMapper emailMapper;
    ResultEntity resultEntity = new ResultEntity<>();//返回类

    //将邮件添加群组
    public ResultEntity addEmailToGroup(int userUid,String groupName,int otherUid,String title) {
        EmailGroupEntity emailGroupEntity = new EmailGroupEntity();
        try {
            emailGroupEntity.setEmailId(emailGroupMapper.addEmailToGroup(userUid, otherUid, title));
            //获得信件唯一id并设置到类中
            emailGroupEntity.setGroupName(groupName);
            emailGroupEntity.setUserUid(userUid);
            //设置信件群组的内容
            emailGroupMapper.insert(emailGroupEntity);
            //插入信件群组
            return resultEntity.construct("加入群组成功", true);
        }catch (Exception e){
            throw new BizException(ErrorEnum.DATA_ERROR.getCode(),"邮件添加群组错误错误");
        }
    }
    //批量删除
    public ResultEntity deletAllGroup(int userUid,String groupName) {
        try {
            List<Integer> list = emailGroupMapper.selectEmailId(userUid, groupName);
            //获得群组中的信件id并储存在列表中
            for (int temp : list) {
                emailGroupMapper.updateCanSee(temp);
                //逐一删除
            }

            return resultEntity.construct("批量删除成功", true);
        }catch (Exception e){
            throw new BizException(ErrorEnum.DATA_ERROR.getCode(),"批量删除错误");
        }
    }

    //展示群组信件
    public ResultEntity showEmailGroup(int userUid,String groupName) {
        try {
            List<Integer> list = emailGroupMapper.selectEmailId(userUid, groupName);
            //获得群组中的信件id并储存在列表中
            List<EmailEntity> group = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                int temp = list.get(i);
                group.add(emailMapper.selectEmailById(temp));
            }
            //将邮件按默认顺序先储存在列表中

            Integer topId = emailGroupMapper.selectEmailIdByTop(userUid, groupName);
            if (topId != null) {
                for (int i = 0; i < list.size(); i++) {
                    if (group.get(i).getId() == topId) {
                        EmailEntity tempEmail = group.get(i);
                        group.set(i, group.get(0));
                        group.set(0, tempEmail);
                        break;
                    }
                }
            }
            //查看是否有置顶邮件并交换置顶

            return resultEntity.construct("群组信件如下", true, group);
        }catch (Exception e){
            throw new BizException(ErrorEnum.DATA_ERROR.getCode(),"展示小组信件错误");
        }
    }

    //置顶信件
    public ResultEntity upEmailInGroup(int userUid,String groupName,int otherUid,String title) {
        try {
            int id = emailGroupMapper.addEmailToGroup(userUid, otherUid, title);
            //获取置顶邮件id
            emailGroupMapper.upEmailInGroup(id, groupName);
            //在邮件群组中标记置顶
            return resultEntity.construct("置顶成功", true);
        }catch (Exception e){
            throw new BizException(ErrorEnum.DATA_ERROR.getCode(),"置顶错误");
        }
    }
}
