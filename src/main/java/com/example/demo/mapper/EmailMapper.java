package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.EmailEntity;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface EmailMapper extends BaseMapper<EmailEntity>
{

    //接收信件并标为已读
    @Update("update email_entity set is_read = 1 where receiver_uid = #{receiverUid}")
    void updateIsReadByReceiverUid(@Param("receiverUid") int receiverUid);

    //分页展示自己发送或收到的信件
    @Select("select * from email_entity where ((receiver_uid = #{receiverUid}) or (sender_uid = #{receiverUid})) and can_see = 0 order by send_time desc")
    List<EmailEntity> showAllEmailEntity(int receiverUid, RowBounds rowBounds);

    //根据标题和内容模糊查询
    @Select("select * from email_entity where ((receiver_uid = #{receiverUid}) or (sender_uid = #{receiverUid})) and can_see = 0 and ((title like concat('%', #{keyword},'%') or content like concat('%', #{keyword},'%'))) order by send_time desc")
    List<EmailEntity> selectEmail(int receiverUid,String keyword);

    //定时发送，更新是否可见与发送时间
    @Update("update email_entity set can_see = 0, send_time = #{newSendTime} where sender_uid = #{senderUid} and send_time = #{oldSendTime}")
    void updateCanSeeAndSendTime(String newSendTime,int senderUid,String oldSendTime);
    
    //根据id展示邮件
    @Select("select * from email_entity where id = #{id};")
    EmailEntity selectEmailById(int id);
}