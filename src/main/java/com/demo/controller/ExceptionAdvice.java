package com.demo.controller;


import com.demo.exceptions.ServiceException;
import com.demo.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public Response<?> handle(Exception e) {
        if (e instanceof ServiceException) {
            return Response.fail(e.getMessage());
        }
        log.error("", e);
        return Response.fail("处理异常，请刷新重试！");
    }
}
