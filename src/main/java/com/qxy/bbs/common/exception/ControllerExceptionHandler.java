package com.qxy.bbs.common.exception;

import com.alibaba.fastjson.JSON;
import com.qxy.bbs.common.domain.WebReslut;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;




/**
 * @author qixiangyang5
 * @create 2020/7/25 18:13
 */
@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {


    @ExceptionHandler(value = NotLoginException.class)
    @ResponseBody
    public WebReslut notLoginException(NotLoginException e) {
        log.error("notLoginException Exception: ", e);
        return WebReslut.failed(2000,"用户未登录");
    }

}
