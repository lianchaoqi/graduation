package com.jack.graduation.exception;

import com.jack.graduation.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @BelongsProject: graduation
 * @BelongsPackage: com.jack.graduation.exception
 * @Author: jack
 * @CreateTime: 2023-01-04  21:45
 * @Description: TODO
 * @Version: jdk1.8
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 如果抛出的的是ServiceException，则调用该方法
     *
     * @param se 业务异常
     * @return Result
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    //捕获  进行返回
    public Result handle(ServiceException se) {
        return Result.error(se.getCode(), se.getMessage());
    }

}