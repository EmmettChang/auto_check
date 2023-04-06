package com.emmett.auto_check.exception;

public enum ApplicationErrorEnum {

    /**
     * 通用错误信息
     */


    COMMON_FAIL("00000", "操作失败"),
    COMMON_REQUEST_PARAMETER_LESS("00001", "请求参数错误"),
    COMMON_PAGE_PARAMETER_NOT_FOUND_ERROR("00004", "分页参数缺失"),
    COMMON_DATA_NOT_FOUND("0006,", "数据不存在"),

    ROLE_EXIST("0005,", "角色已存在"),

    ROLE_NAME_EXIST("0007,", "角色名称已存在"),
    ROLE_CODE_EXIST("0008,", "角色编码已存在"),


    USER_NOT_FOUND("0006", "用户不存在"),
    USER_NOT_HAVE_ANY_ROLES("0007", "用户没有任何角色"),
    USER_NOT_HAVE_ANY_AUTHORITY("0008", "用户没有任何权限"),
    USER_NOT_HAVE_ANY_VALID_ROLES("0008", "用户没有任何有效角色"),

    DEPENDENCY_EXIST("0010", "存在依赖，不能删除"),

    THRIDAPP_CONNECTION_LOST("T101", "连接中断");


    private final String resCode;
    private final String resMsg;

    ApplicationErrorEnum(String resCode, String resMsg) {
        this.resCode = resCode;
        this.resMsg = resMsg;
    }

    public String getResCode() {
        return resCode;
    }

    public String getResMsg() {
        return resMsg;
    }
}
