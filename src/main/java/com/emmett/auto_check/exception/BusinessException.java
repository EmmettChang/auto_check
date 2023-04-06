package com.emmett.auto_check.exception;

public class BusinessException extends RuntimeException {
    private String msg;

    public BusinessException(ApplicationErrorEnum error) {
        this.msg = error.getResMsg();
    }

    public BusinessException(String errorMsg) {
        super(errorMsg);
        this.msg = errorMsg;
    }

    @Override
    public String getMessage() {
        return msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public BusinessException() {
        super();
    }
}
