package com.emmett.auto_check.domain;

import lombok.Data;

@Data
public class CompleteResultBody {
    private __sys__ __sys__;

    @Data
    public static class __sys__ {
        private String msg;
        private String detailMsg;
        private int status;
    }



}
