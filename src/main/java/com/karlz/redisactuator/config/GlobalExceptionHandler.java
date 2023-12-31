package com.karlz.redisactuator.config;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

public class GlobalExceptionHandler {
    @Resource
    private PrometheusCustomMonitor monitor;

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public String handle(Exception e) {
        monitor.getRequestErrorCount().increment();
        return "error, message: " + e.getMessage();
    }
}
