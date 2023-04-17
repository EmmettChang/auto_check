package com.emmett.auto_check.domain;

import lombok.Data;

import java.util.List;

@Data
public class CompletedRequestBody {

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

        private Inqu_status inqu_status;
        private Detail1 detail1;
        private Result result;
        private ResultXc resultXc;

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
        public static class Result {

            private Attr attr;
            private Meta meta;
            private List<List<String>> rows;

        }

        @Data
        public static class ResultXc {

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

            @Data
            public static class Columns {

                private int pos;
                private String name;
                private String descName;
                private String displayType;
                private int width;
            }
        }
    }
}