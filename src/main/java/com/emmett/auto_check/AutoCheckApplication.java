package com.emmett.auto_check;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@RequiredArgsConstructor
@SpringBootApplication(scanBasePackages = "com.emmett.auto_check")
@EnableScheduling
@EnableAsync
@ServletComponentScan
public class AutoCheckApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutoCheckApplication.class, args);
    }

}
