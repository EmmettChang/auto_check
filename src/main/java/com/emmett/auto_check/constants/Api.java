package com.emmett.auto_check.constants;

/**
 *  API 接口地址
 */
public interface Api {
    // 登录接口
    String LoginRequestUrl = "http://10.200.24.172/eqms/login?p_username=%s&p_password=%s";
    String QCRT0101RequestUrl = "http://10.200.24.172/eqms/web/QCRT0101";
    String queryTaskRequestUrl = "http://10.200.24.172/eqms/service/QCRT0101/query";
    String queryTaskDetailRequestUrl = "http://10.200.24.172/eqms/service/QCRT0101/queryXC";
    String updateTaskDetailRequestUrl = "http://10.200.24.172/eqms/service/QCRT0101/updateXc";
    String completedRequestUrl = "http://10.200.24.172/eqms/service/QCRT0101/completed";
}
