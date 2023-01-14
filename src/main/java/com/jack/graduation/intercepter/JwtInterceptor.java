package com.jack.graduation.intercepter;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.jack.graduation.bean.User;
import com.jack.graduation.common.Constants;
import com.jack.graduation.exception.ServiceException;
import com.jack.graduation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @BelongsProject: graduation
 * @BelongsPackage: com.jack.graduation.intercepter
 * @Author: jack
 * @CreateTime: 2023-01-05  14:29
 * @Description: TODO token验证方法 拦截器
 * @Version: jdk1.8
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        //如果不是映射方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        // token认证
        if (StrUtil.isBlank(token)) {
            throw new ServiceException(Constants.CODE_401, "无token，请登录！");
        }

        // 获取 token 中的 user id
        String userId = null;
        try {
            //拿到token中的user id
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException jwtDecodeException) {
            throw new ServiceException(Constants.CODE_401, "token验证失败，请重新登录！");
        }
        //根据userId查询用户
        User user = userService.getById(userId);
        if (user == null) {
            throw new ServiceException(Constants.CODE_401, "用户不存在，请登录！");
        }

        //用户密码加签验证token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new ServiceException(Constants.CODE_401, "token验证失败，请重新登录");
        }
        return true;
    }


}
