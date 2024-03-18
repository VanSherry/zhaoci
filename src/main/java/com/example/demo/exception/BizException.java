package com.example.demo.exception;

import com.example.demo.error.ErrorEnum;
import lombok.Data;

@Data
public class BizException extends RuntimeException{
    private String errorCode;

    public BizException(String msg){
        super(msg);
    }

    public BizException(String errorCode,String msg){
        super(msg);
        this.errorCode = errorCode;
    }

    public BizException(String errorCode,String msg,Throwable throwable){
        super(msg,throwable);
        this.errorCode = errorCode;
    }

    public BizException(ErrorEnum errorEnum){
        super(errorEnum.getMessage());
        this.errorCode = errorEnum.getCode();
    }

}
