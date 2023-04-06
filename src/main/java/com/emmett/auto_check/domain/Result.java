package com.emmett.auto_check.domain;

/**
 * @author Emmett
 * @version v1.0
 * <p>返回结果类</p>
 */

public class Result<T> {

    /**
     * 返回代码 200 成功，400 失败
     */
    private int returnCode = 200;

    /**
     * 返回信息
     */
    private String returnMsg = "操作成功";
    private T returnData;

    private Boolean isSuccess;

    public static <T> Result<T> ok() {
        return new Result<T>();
    }

    public static <T> Result<T> ok(T returnData) {
        Result<T> result = new Result<T>();
        result.setReturnCode(200);
        result.setReturnData(returnData);
        return result;
    }

    public static <T> Result<T> ok(String returnMsg, T returnData) {
        Result<T> result = new Result<T>();
        result.setReturnCode(200);
        result.setReturnMsg(returnMsg);
        result.setReturnData(returnData);
        return result;
    }

    public static <T> Result<T> err(String returnMsg) {
        Result<T> result = new Result<T>();
        result.setReturnCode(400);
        result.setReturnMsg(returnMsg);
        return result;
    }

    public static <T> Result<T> err(String returnMsg, int returnCode) {
        Result<T> result = new Result<T>();
        result.setReturnCode(returnCode);
        result.setReturnMsg(returnMsg);
        return result;
    }

    /**
     * 构造方法
     * @param returnData 返回数据
     */
    public Result(T returnData) {
        this.returnMsg = "操作成功";
        this.returnData = returnData;
    }

    public Result(int returnCode, String returnMsg) {
        this.returnCode = returnCode;
        this.returnMsg = returnMsg;
    }

    public Result(int returnCode, String returnMsg, T returnData) {
        this.returnCode = returnCode;
        this.returnMsg = returnMsg;
        this.returnData = returnData;
    }

    public Result() {
        super();
    }

    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public T getReturnData() {
        return returnData;
    }

    public void setReturnData(T returnData) {
        this.returnData = returnData;
    }

    public boolean isSuccess() {
        return 200 == returnCode;
    }
}
