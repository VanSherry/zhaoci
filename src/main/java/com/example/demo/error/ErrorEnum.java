package com.example.demo.error;

public enum ErrorEnum implements ErrorCode {

    SUCCESS("SUCCESS","成功"),
    REQUEST_FAIL("REQUEST_FAIL","请求失败"),
    SYSTEM_ERROR("SYSTEM_ERROR","系统异常"),
    OP_TIMEOUT("OP_TIMEOUT","操作超时，请重试"),
    OP_CONFLICT("OP_CONFLICT","操作冲突"),
    DB_ERROR("DB_ERROR","数据库执行错误"),
    PARAMETER_ERROR("PARAMETER_ERROR","参数错误"),
    NO_PRIVILEGE("NO_PRIVILEGE","没有权限"),
    DATA_ERROR("DATA_ERROR","数据异常"),
    DATA_NOT_FOUND("DATA_NOT_FOUND","数据不存在"),
    DATA_EXIST("DATA_EXIST","数据已存在");

    private String code;

    private String message;

    ErrorEnum(String code,String message){
        this.code = code;
        this.message = message;
    }
    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
