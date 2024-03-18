package com.example.demo.entity;

import lombok.*;

//可能会用到的结果类，为过滤器与jwt使用
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ResultEntity<T> {
    private Boolean success = false;
    private String msg;
    private T data;
    private String code = null;

    public ResultEntity<T> construct(String msg,Boolean success){
        this.msg=msg;
        this.success=success;
        return this;
    }

    public ResultEntity<T> construct(String msg,Boolean success,T data){
        this.msg=msg;
        this.success=success;
        this.data=data;
        return this;
    }

    public ResultEntity<T> result(String code,String msg,T data){
        this.msg=msg;
        this.code=code;
        this.data=data;
        return this;
    }

}
