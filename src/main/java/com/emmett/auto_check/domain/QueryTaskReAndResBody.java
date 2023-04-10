package com.emmett.auto_check.domain;

import lombok.Data;

import java.util.List;

@Data
public class QueryTaskReAndResBody {

    private String efFormEname;
    private String efFormCname;
    private String efFormPopup;
    private String efFormTime;
    private String efCurFormEname;
    private String efCurButtonEname;
    private String packageName;
    private String serviceName;
    private String methodName;
    private String efFormInfoTag;
    private String efFormLoadPath;
    private String efFormButtonDesc;
    private String __$$DIAGNOSE$$__;
    private String efSecurityToken;
    private String COOKIE;
    private String __version__;
    private QueryTaskReAndResBody_Sys __sys__;
    private QueryTaskReAndResBody_Blocks __blocks__;

    @Data
    public static class QueryTaskReAndResBody_Sys {

        private String name;
        private String descName;
        private String msg;
        private String msgKey;
        private String detailMsg;
        private int status;
        private String traceId;
    }

    @Data
    public static class QueryTaskReAndResBody_Blocks {

        private Inqu_status inqu_status;
        private Detail1 detail1;
        private Result result;

        @Data
        public static class Inqu_status {
            private Attr attr;
            private Meta meta;
            private List<List<String>> rows;

        }

        @Data
        public static class Detail1 {
            private Attr attr;
            private Meta meta;
            private List<List<String>> rows;
        }

        @Data
        public static class Attr {

        }

        @Data
        public static class Meta {
            private String desc;
            private Attr attr;
            private List<Columns> columns;
        }

        @Data
        public static class Columns {
            private int pos;
            private String name;
            private String descName;
        }

        @Data
        public static class Result {
            private Attr attr;
            private Meta meta;
            private List<List<String>> rows;

            @Data
            public static class Attr {
                private String showCount;
                private int offset;
                private int limit;
                private int count;
                private String orderBy;
            }
        }
    }
}