package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.EmailEntity;
import com.example.demo.entity.EmailGroupEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface EmailGroupMapper extends BaseMapper<EmailGroupEntity> {

    //获取邮件唯一id
    @Select("select id from email_entity where sender_uid = #{senderUid} and receiver_uid = #{receiverUid} and title = #{title}")
    int addEmailToGroup(int senderUid, int receiverUid, String title);

    //获取群组中的邮件id
    @Select("select email_id from email_group_entity where user_uid =#{userUid} and group_name = #{groupName} and flag = 0")
    List<Integer> selectEmailId(int userUid,String groupName);

    //删除信件
    @Update("update email_entity set can_see = 1 where id = #{temp}")
    void updateCanSee(int temp);

    //将邮件置顶
    @Update("update email_group_entity set top = 1 where email_id = #{id} and group_name = #{groupName}")
    void upEmailInGroup(int id,String groupName);

    //查找置顶邮件id
    @Select("select email_id from email_group_entity where user_uid = #{userUid} and group_name = #{groupName} and top = 1")
    Integer selectEmailIdByTop(int userUid,String groupName);
}