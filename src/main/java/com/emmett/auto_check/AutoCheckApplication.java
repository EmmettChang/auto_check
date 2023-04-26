package com.emmett.auto_check;

import com.emmett.auto_check.config.SYSConfig;
import com.emmett.auto_check.service.impl.AutoCheckService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.swing.*;
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
        System.setProperty("java.awt.headless", "false");
        try(Reader reader = new FileReader("D:\\AutoCheck\\Sys.config")) {
            SYSConfig sysConfig = new Gson().fromJson(reader, SYSConfig.class);
            sysConfig.loadDefault();
            AutoCheckService a = new AutoCheckService();
            for (SYSConfig.User user: sysConfig.getUsers()) {
                a.doCheck(user, sysConfig);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage()
                    + "\n\n如不能自行解决，不要关闭此窗口，截图联系yin_zjl@foxmail.com", "别动！", JOptionPane.ERROR_MESSAGE);
            throw new RuntimeException(e);
        }
        System.exit(0);
    }

}
