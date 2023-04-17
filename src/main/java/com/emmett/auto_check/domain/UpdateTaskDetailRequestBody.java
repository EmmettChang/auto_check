package com.emmett.auto_check.domain;

import lombok.Data;

import java.util.List;

@Data
public class UpdateTaskDetailRequestBody {

    private __blocks__ __blocks__;
    private __sys__ __sys__;
    private String __version__;

    @Data
    public static class __blocks__ {
        private Detail1 detail1;
        private Inqu_status inqu_status;
        private Result result;
        private ResultXc resultXc;

        @Data
        public static class Detail1 {
            private Attr attr;
            private Meta meta;
            private List<List<String>> rows;

        }
        @Data
        public static class Inqu_status {
            private Attr attr;
            private Meta meta;
            private List<List<String>> rows;

        }
        @Data
        public static class Result {
            private Attr attr;
            private ResultMeta meta;
            private List<List<String>> rows;
        }

        @Data
        public static class ResultXc {
            private Attr attr;
            private ResultMeta meta;
            private List<List<String>> rows;
        }
    }

    @Data
    public static class __sys__ {
        private String descName;
        private String detailMsg;
        private String msg;
        private String msgKey;
        private String name;
        private String status;
        private String traceId;
    }

    @Data
    public static class Attr {};

    @Data
    public static class Meta {
        private Attr attr;
        private List<Column> columns;
        private String desc;

        @Data
        public static class Column {
            private String name;
            private int pos;
        }
    }

    @Data
    public static class ResultMeta {
        private Attr attr;
        private List<Column> columns;
        private String desc;

        @Data
        public static class Column {
            private String descName;
            private String displayType;
            private String name;
            private int pos;
            private int width;
        }
    }

}
