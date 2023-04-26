package com.emmett.auto_check.config;

import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Data
public class SYSConfig {
    private List<User> users;
    private String beginTime;
    private String endTime;
    private List<String> opData;
    private String opValue;
    private int limit = 200;

    @Data
    public static class User {

        private String id;
        private String name;
        private String password;
    }

    public void loadDefault() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // 获取当月第一天
//        LocalDate firstDayOfMonth = LocalDate.now().withDayOfMonth(1);
//        String beginTime = sysConfig.getBeginTime().equals("") ? firstDayOfMonth.format(dateTimeFormatter) : sysConfig.getBeginTime();

        // 获取今天
        LocalDate today = LocalDate.now();
        if (Objects.equals(this.beginTime, ""))
            this.beginTime = today.format(dateTimeFormatter);
        this.endTime = today.format(dateTimeFormatter);
    }

}
