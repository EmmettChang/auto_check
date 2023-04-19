package com.emmett.auto_check;

import com.emmett.auto_check.config.SYSConfig;
import com.emmett.auto_check.service.impl.AutoCheckService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.FileReader;
import java.io.Reader;

@RequiredArgsConstructor
@SpringBootApplication(scanBasePackages = "com.emmett.auto_check")
@EnableScheduling
@EnableAsync
@ServletComponentScan
@Slf4j
public class AutoCheckApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutoCheckApplication.class, args);
        try(Reader reader = new FileReader("D:\\AutoCheck\\SysConfig.txt")) {
            SYSConfig sysConfig = new Gson().fromJson(reader, SYSConfig.class);
            AutoCheckService a = new AutoCheckService();
            for (SYSConfig.User user: sysConfig.getUsers()) {
                a.doCheck(user.getId(), user.getPassword(), sysConfig);
            }
        } catch (Exception e) {
            Logger logger = LoggerFactory.getLogger("E");
            logger.info(e.getMessage());
            log.info(e.getMessage());
        }

    }

}
