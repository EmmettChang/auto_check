package com.emmett.auto_check.config;
import com.alibaba.fastjson.JSONObject;
import com.emmett.auto_check.domain.Result;
import com.emmett.auto_check.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApplicationControllerAdvice {
    private Logger logger = LoggerFactory.getLogger(ApplicationControllerAdvice.class);


    @ExceptionHandler(BusinessException.class)
    public Result<?> robotExceptionDeal(BusinessException re) {
        if(logger.isErrorEnabled()){
            re.printStackTrace();
            logger.error("业务错误信息:{}",re.getMessage());
        }
        Result<?> response = new Result<>();
        response.setReturnCode(400);
        response.setReturnMsg(re.getMessage());
        if(logger.isInfoEnabled()) {
            logger.info("请求返回结果为【{}】", JSONObject.toJSONString(response));
        }
        return  response;

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> argumentValidHandler(MethodArgumentNotValidException re) {
        if(logger.isErrorEnabled()){
            logger.error("业务错误信息:{}",re);
        }

        Result<?> response = new Result();
        response.setReturnCode(403);

        BindingResult result = re.getBindingResult();
        List<String> errList ;

        if (result != null) {
            errList = result.getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.toList());
            response.setReturnMsg(String.join("\n", errList));
        } else {
            response.setReturnMsg(new String("argument error"));
        }

        if(logger.isInfoEnabled()) {
            logger.info("请求返回结果为【{}】", JSONObject.toJSONString(response));
        }
        return  response;
    }


    @ExceptionHandler(RuntimeException.class)
    public Result<?> runtimeExceptionDeal(RuntimeException re) {
        if(logger.isErrorEnabled()){
            logger.error("其他运行时错误信息:{}",re);
        }
        Result<?> response = new Result<>();
        response.setReturnCode(400);
        response.setReturnMsg("系统运行错误,请联系管理员");
        if(logger.isInfoEnabled()) {
            logger.info("请求返回结果为【{}】", JSONObject.toJSONString(response));
        }
        return  response;
    }
}
