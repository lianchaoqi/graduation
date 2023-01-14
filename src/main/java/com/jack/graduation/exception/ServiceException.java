package com.jack.graduation.exception;

/**
 * @BelongsProject: graduation
 * @BelongsPackage: com.jack.graduation.exception
 * @Author: jack
 * @CreateTime: 2023-01-04  21:43
 * @Description: TODO
 * @Version: jdk1.8
 */

import lombok.Getter;

/**
 * 自定义异常
 */
@Getter
public class ServiceException extends RuntimeException {
    private String code;

    public ServiceException(String code, String msg) {
        super(msg);
        this.code = code;
    }
}