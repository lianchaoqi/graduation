package com.jack.graduation.common;

/**
 * @BelongsProject: graduation
 * @BelongsPackage: com.jack.graduation.common
 * @Author: jack
 * @CreateTime: 2023-01-04  20:58
 * @Description: TODO
 * @Version: jdk1.8
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 接口统一返回包装类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    private String code;
    private String msg;
    private Object data;

    public static Result success() {
        return new Result(Constants.CODE_200, "", null);
    }

    public static Result success(Object data) {
        return new Result(Constants.CODE_200, "", data);
    }

    public static Result error(String code, String msg) {
        return new Result(code, msg, null);
    }

    public static Result error() {
        return new Result(Constants.CODE_500, "系统错误", null);
    }

    public static Result error(String code, String msg, Object data) {
        return new Result(Constants.CODE_500, "系统错误", data);
    }
}

