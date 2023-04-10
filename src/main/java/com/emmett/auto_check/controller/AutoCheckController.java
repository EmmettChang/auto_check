package com.emmett.auto_check.controller;

import com.emmett.auto_check.domain.Result;
import com.emmett.auto_check.service.IAutoCheckService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/autoCheck/")
public class AutoCheckController {

    @Autowired
    IAutoCheckService autoCheckService;
    @PostMapping("/doCheck")
    public Result<?> doCheck() {
        autoCheckService.doCheck();
        return Result.ok();
    }
}
