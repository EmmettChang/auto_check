package com.emmett.auto_check.config;

import lombok.Data;

import java.util.List;

@Data
public class SYSConfig {
    private List<User> users;
    private String beginTime;
    private String endTime;
    private List<String> opData;
    private String opValue;

    @Data
    public static class User {

        private String id;
        private String password;
    }
}
