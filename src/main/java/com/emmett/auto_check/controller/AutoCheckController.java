package com.emmett.auto_check.controller;

import com.emmett.auto_check.domain.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/autoCheck/")
public class AutoCheckController {

    @Value("${login.id}")
    private String id;
    @Value("${login.name}")
    private String name;
    @PostMapping("/doCheck")
    public Result<?> doCheck() {
        return Result.ok();
    }
}
