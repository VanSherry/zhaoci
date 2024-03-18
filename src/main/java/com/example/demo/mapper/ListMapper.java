package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.ListEntity;
import com.example.demo.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;
import org.yaml.snakeyaml.scanner.Constant;

import java.util.List;

@Mapper
public interface ListMapper extends BaseMapper<ListEntity> {

    @Update("update list_entity set remark = #{remark} ${ew.customSqlSegment}")//给好友备注
    void updateRemark(@Param("ew") QueryWrapper<ListEntity> wrapper, @Param("remark") String remark);

    @Update("update list_entity set relationship = #{relationship} ${ew.customSqlSegment}")//设置亲密关系
    void setRelationship(@Param("ew") QueryWrapper<ListEntity> wrapper,@Param("relationship") String relationship);


    @Update("update list_entity set status = 1 ${ew.customSqlSegment}")//假删除好友
    void deleteFriend(@Param("ew") QueryWrapper<ListEntity> wrapper);

    @Select("select user_uid from list_entity where friend_uid = #{friendUid} and is_friend = 0")//查看好友申请
    List<Integer> checkWhoAdd(int friendUid);

    @Update("update list_entity set is_friend = 1 where user_uid = #{userUid} and friend_uid = #{friendUid} and is_friend = 0")//同意好友申请
    void agreeFriendAddUser(int userUid,int friendUid);

    @Select("select * from list_entity where user_uid = #{userUid} and is_friend = 1 and status = 0")
    List<ListEntity> showAllFriend(int userUid, RowBounds rowBounds);

}
