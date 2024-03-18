package com.example.demo.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ToolUtils {
    public static SqlSession getSqlSession(){
        SqlSession sqlSession = null;
        try {
            InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
            sqlSession = sqlSessionFactory.openSession(true);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return sqlSession;
    }

    public static String MD5(String password){//密码加密方法
        try{
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[]result=digest.digest(password.getBytes());
            StringBuffer buffer = new StringBuffer();

            for(byte b:result){
                int number=b&0xff;
                String str = Integer.toHexString(number);
                if(str.length()==1){
                    buffer.append("0");
                }
                buffer.append(str);
            }
            return buffer.toString();
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            return "";
        }
    }
}
