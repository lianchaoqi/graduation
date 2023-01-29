package com.jack.graduation.config;

import com.jack.graduation.intercepter.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @BelongsProject: graduation
 * @BelongsPackage: com.jack.graduation.config
 * @Author: jack
 * @CreateTime: 2023-01-05  15:44
 * @Description: TODO
 * @Version: jdk1.8
 */

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getJwtInterceptor())
                .addPathPatterns("/**")  // 拦截所有请求，通过判断token是否合法来决定是否需要登录
                .excludePathPatterns("/user/userLogin",
                                    "/user/registerUser",
                                    "/**/exportUser",
                                    "/file/**",
                                    "/echarts/**",
                                    "/status/**",
                                    "/hadoopStatus",
                                    "/sparkCon/**",
                                    "/file/uploadToHdfs");//放行配置
    }

    //注入容器进行获取
    @Bean
    public JwtInterceptor getJwtInterceptor() {
        return new JwtInterceptor();
    }
}