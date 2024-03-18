package com.example.demo.exception.advice;

import com.example.demo.entity.ResultEntity;
import com.example.demo.exception.BizException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonExceptionHandler {



    @ExceptionHandler(BizException.class)
    public ResultEntity handleBizException(BizException e) {
        ResultEntity resultEntity = new ResultEntity<>();
        System.out.println("记录exception");
        return resultEntity.result(e.getMessage(),e.getErrorCode(),null);
    }
}
