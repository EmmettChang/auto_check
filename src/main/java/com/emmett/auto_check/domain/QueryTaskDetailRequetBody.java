package com.emmett.auto_check.domain;

import lombok.Data;
@Data
public class QueryTaskDetailRequetBody {

    private String checkStandardId;
    private String checkPlanInternalCode;
    private String __version__;
    private __sys__ __sys__;
    private __blocks__ __blocks__;

    @Data
    public static class __sys__ {

        private String name;
        private String descName;
        private String msg;
        private String msgKey;
        private String detailMsg;
        private int status;
        private String traceId;

    }

    @Data
    public static class __blocks__ {

    }
}