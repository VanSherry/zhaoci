package com.example.demo.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.example.demo.entity.EmailEntity;
import com.example.demo.entity.ResultEntity;
import com.example.demo.error.ErrorEnum;
import com.example.demo.exception.BizException;
import com.example.demo.mapper.EmailGroupMapper;
import com.example.demo.mapper.EmailMapper;
import com.example.demo.mapper.UserMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private EmailMapper emailMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private EmailGroupMapper emailGroupMapper;
    ResultEntity resultEntity = new ResultEntity<>();
    //定时器需要
    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    //发送信件
    public ResultEntity sendEmail(int senderUid,int receiverUid,String title,String content) {
        EmailEntity emailEntity = new EmailEntity();
        Date data = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        //获取发件时间并转化成年月日时格式

        try {
            emailEntity.setSenderUid(senderUid);
            emailEntity.setReceiverUid(receiverUid);
            emailEntity.setTitle(title);
            emailEntity.setContent(content);
            emailEntity.setSendTime(formatter.format(data));
            //设置邮件内容
            emailMapper.insert(emailEntity);
            //插入到数据库中
            return resultEntity.construct("发送成功", true);
        }catch (Exception e){
            throw new BizException(ErrorEnum.DATA_ERROR.getCode(),"发送信件错误");
        }
    }

    //接收信件并分页展示
    public ResultEntity receiveEmail(int receiverUid,int pageNum) {
        int pageSize = 5;
        //一页有几条邮件
        try {
            emailMapper.updateIsReadByReceiverUid(receiverUid);
            //将邮件标记为已读
            RowBounds rowBounds = new RowBounds((pageNum - 1) * pageSize, pageSize);
            return resultEntity.construct("收件箱：", true, emailMapper.showAllEmailEntity(receiverUid, rowBounds));
            //分页展示所有邮件
        }catch (Exception e){
            throw new BizException(ErrorEnum.DATA_ERROR.getCode(),"接收信件错误");
        }
    }

    //根据标题和内容模糊查询
    public ResultEntity selectEmail(int receiverUid,String keyword){
        try {
            return resultEntity.construct("查询结果", true, emailMapper.selectEmail(receiverUid, keyword));
            //模糊查询并返回
        }catch (Exception e){
            throw new BizException(ErrorEnum.DATA_ERROR.getCode(),"模糊查询错误");
        }
    }

    //漂流瓶功能
    public ResultEntity driftEmail(int senderUid,String title,String content) {
        EmailEntity emailEntity = new EmailEntity();
        Date data = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        //获取发件时间并转化成年月日时格式

        try {
            emailEntity.setSenderUid(senderUid);
            emailEntity.setReceiverUid(userMapper.getUidRandom(senderUid));
            //随机获取收件人
            emailEntity.setTitle(title);
            emailEntity.setContent(content);
            emailEntity.setSendTime(formatter.format(data));
            //设置邮件内容
            emailMapper.insert(emailEntity);
            //插入到数据库中
            return resultEntity.construct("发送成功", true);
        }catch (Exception e){
            throw new BizException(ErrorEnum.DATA_ERROR.getCode(),"漂流瓶错误");
        }
    }

    //定时发送
    public ResultEntity timeEmail(int senderUid,int receiverUid,String title,String content,int time) {
        try {
            EmailEntity emailEntity = new EmailEntity();
            Date now = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            //获取发件时间并转化成年月日时格式
            String oldSendTime = formatter.format(now);
            //发送
            emailEntity.setSenderUid(senderUid);
            emailEntity.setReceiverUid(receiverUid);
            emailEntity.setTitle(title);
            emailEntity.setContent(content);
            emailEntity.setSendTime(oldSendTime);
            emailEntity.setCanSee(1);
            //设置邮件为不可见
            emailMapper.insert(emailEntity);

            //收到
            executorService.schedule(() -> {
                emailMapper.updateCanSeeAndSendTime(formatter.format(new Date()), senderUid, oldSendTime);
            }, time, TimeUnit.MINUTES);
            //延迟更新，使邮件可见

            return resultEntity.construct("发送成功", true);
        }catch (Exception e){
            throw new BizException(ErrorEnum.DATA_ERROR.getCode(),"定时发送错误");
        }
    }

    //删除信件
    public ResultEntity deleteEmail(int senderUid, int receiverUid, String title) {
        try {
            int id = emailGroupMapper.addEmailToGroup(senderUid, receiverUid, title);
            //获取邮件id
            emailGroupMapper.updateCanSee(id);
            //根据id假删除邮件
            return resultEntity.construct("删除成功", true);
        }catch (Exception e){
            throw new BizException(ErrorEnum.DATA_ERROR.getCode(),"删除错误");
        }
    }
}
