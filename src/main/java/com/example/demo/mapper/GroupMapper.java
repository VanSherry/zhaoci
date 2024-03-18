package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.GroupEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GroupMapper extends BaseMapper<GroupEntity> {
    @Select("select friend_uid from group_entity where user_uid = #{userUid} and group_name = #{groupName}")
    List<Integer> sendEmailToAll(int userUid,String groupName);
}
