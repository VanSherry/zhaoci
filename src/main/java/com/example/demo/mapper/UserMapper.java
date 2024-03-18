package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.UserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {
    /**
     * 添加用户信息
     */
    @Insert("insert into user values(null,#{userName},#{password})")
    int insertUser(String userName,String password);
    /**
     * 查询uid
     */
    @Select("select * from user where password = #{password} and userName = #{userName}")
    int getUid(String password,String userName);
    /**
      登录验证
     */
    @Select("select count(*) from user where uid = #{uid} and password = #{password}")
    boolean loginByUid(Integer uid, String password);

    @Select("select count(*) from user where userName = #{userName} and password = #{password}")
    boolean loginByUserName(String userName,String password);

    /**
    根据uid查询用户名
     */
    @Select("select username from user where uid = #{uid}")
    String getUsernameByUid(int uid);

    //随机返回一个uid
    @Select("select uid from user where uid != #{senderUid} order by rand() limit 1")
    int getUidRandom(int senderUid);
}
