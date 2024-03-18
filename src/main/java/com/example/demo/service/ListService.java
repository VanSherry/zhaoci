package com.example.demo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.entity.ListEntity;
import com.example.demo.entity.ResultEntity;
import com.example.demo.error.ErrorEnum;
import com.example.demo.exception.BizException;
import com.example.demo.mapper.ListMapper;
import com.example.demo.mapper.UserMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListService {
    @Autowired
    private ListMapper listMapper;
    @Autowired
    private UserMapper userMapper;
    ResultEntity resultEntity = new ResultEntity<>();
    //添加好友
    public ResultEntity insertFriend(int friendUid, int userUid){
        try {
            ListEntity listEntity = new ListEntity();
            listEntity.setFriendUid(friendUid);
            listEntity.setUsername(userMapper.getUsernameByUid(friendUid));
            //根据id查找用户名并储存
            listEntity.setUserUid(userUid);
            //插入到数据库中
            if (listEntity.getUsername()!=null){
                listMapper.insert(listEntity);
                return resultEntity.construct("添加成功", true);
            }else{
                return resultEntity.construct("uid错误", false);
            }
        }catch (Exception e){
            throw new BizException(ErrorEnum.DATA_ERROR.getCode(),"朋友uid错误");
        }
    }

    //给好友备注
    public ResultEntity updateRemark(int userUid,int friendUid,String remark) {
        try {
            QueryWrapper<ListEntity> wrapper = new QueryWrapper<ListEntity>().eq("user_uid", userUid).eq("friend_uid", friendUid);
            //mybatis-plus拼接自定义sql语句(后来发现太麻烦，就没用了）
            listMapper.updateRemark(wrapper, remark);
            //传入自定义的where条件并执行sql语句
            return resultEntity.construct("添加备注成功", true);
        }catch (Exception e){
            throw new BizException(ErrorEnum.DATA_ERROR.getCode(),"添加备注错误");
        }
    }

    //设置亲密关系
    public ResultEntity setRelationship(int userUid,int friendUid,String relationship) {
        try {
            QueryWrapper<ListEntity> wrapper = new QueryWrapper<ListEntity>().eq("user_uid", userUid).eq("friend_uid", friendUid);
            //mybatis-plus拼接自定义sql语句
            listMapper.setRelationship(wrapper, relationship);
            //传入自定义的where条件并执行sql语句
            return resultEntity.construct("设置亲密关系成功", true);
        }catch (Exception e){
            throw new BizException(ErrorEnum.DATA_ERROR.getCode(),"设置亲密关系错误");
        }
    }

    //假删除好友
    public ResultEntity deleteFriend(int userUid,int friendUid) {
        try {
            QueryWrapper<ListEntity> wrapper = new QueryWrapper<ListEntity>().eq("user_uid", userUid).eq("friend_uid", friendUid);
            //mybatis-plus拼接自定义sql语句
            listMapper.deleteFriend(wrapper);
            //传入自定义的where条件并执行sql语句
            return resultEntity.construct("成功假删除好友", true);
        }catch (Exception e){
            throw new BizException(ErrorEnum.DATA_ERROR.getCode(),"假删除错误");
        }
    }

    //查看好友申请
    public ResultEntity checkWhoAdd(int friendUid) {
        try {
            return resultEntity.construct("好友申请：", true, listMapper.checkWhoAdd(friendUid));
            //查看好友申请并返回申请人信息
        }catch (Exception e){
            throw new BizException(ErrorEnum.DATA_ERROR.getCode(),"好友UID错误");
        }
    }

    //同意好友申请
    public ResultEntity agreeFriendAdd(int userUid,int friendUid) {
        try {
            listMapper.agreeFriendAddUser(friendUid, userUid);
            //同意好友申请

            ListEntity listEntity = new ListEntity();
            listEntity.setFriendUid(friendUid);
            listEntity.setUsername(userMapper.getUsernameByUid(friendUid));
            listEntity.setUserUid(userUid);
            listEntity.setIsFriend(1);
            listMapper.insert(listEntity);
            //将好友添加
            return resultEntity.construct("同意好友申请成功", true);
        }catch (Exception e){
            throw new BizException(ErrorEnum.DATA_ERROR.getCode(),"同意好友申请错误");
        }
    }

    //查看好友列表
    public ResultEntity showAllFriend(int userUid,int pageNum) {
        int pageSize = 10;
        //一页可显示10个好友
        try {
            RowBounds rowBounds = new RowBounds((pageNum - 1) * pageSize, pageSize);
            return resultEntity.construct("好友列表", true, listMapper.showAllFriend(userUid, rowBounds));
            //分页展示好友
        }catch (Exception e){
            throw new BizException(ErrorEnum.DATA_ERROR.getCode(),"好友列表展示错误");
        }
    }
}
