package com.jack.graduation.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.jack.graduation.bean.User;
import com.jack.graduation.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @BelongsProject: graduation
 * @BelongsPackage: com.jack.graduation.utils
 * @Author: jack
 * @CreateTime: 2023-01-05  12:32
 * @Description: TODO
 * @Version: jdk1.8
 */

@Component
public class TokenUtil {

    private static UserService staticUserService;

    //服务器启动时就加载到容器，赋给静态变量staticUserService，供给静态方法调用
    @Resource
    private UserService userService;

    @PostConstruct
    public void setUserService() {
        staticUserService = userService;
    }

    //token工具
    public static String getToken(String userId, String sign) {
        return JWT.create().withAudience(userId)  //绑定id作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2))  //两小时过期
                .sign(Algorithm.HMAC256(sign));  //一password作为秘钥
    }


    /**
     * 获取当前登录的用户信息
     *
     * @return user对象
     */
    public static User getCurrentUser() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            if (StrUtil.isNotBlank(token)) {
                String userId = JWT.decode(token).getAudience().get(0);
                return staticUserService.getById(Integer.valueOf(userId));
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
