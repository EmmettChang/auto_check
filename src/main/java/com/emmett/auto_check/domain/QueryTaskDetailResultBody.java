package com.emmett.auto_check.domain;

import lombok.Data;

import java.util.List;

@Data
public class QueryTaskDetailResultBody {

    private __sys__ __sys__;
    private String __version__;
    private __blocks__ __blocks__;

    @Data
    public static class __sys__ {
        private String msg;
        private String traceId;
        private String detailMsg;
        private String msgKey;
        private int status;
    }

    @Data
    public static class __blocks__ {
        private ResultXc resultXc;

        @Data
        public static class ResultXc {
            private Meta meta;
            private List<List<String>> rows;

            @Data
            public static class Meta {
                private List<Columns> columns;

                @Data
                public static class Columns {
                    private int pos;
                    private String name;
                    private String descName;
                }
            }
        }
    }
}