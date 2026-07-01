package com.catlife.config;

import com.catlife.common.jwt.JwtAuthenticationFilter;
import com.catlife.common.jwt.JwtUtil;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Web MVC 配置
 * 注册 JWT 认证过滤器
 */
@Configuration
public class WebMvcConfig {

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtUtil jwtUtil) {
        return new JwtAuthenticationFilter(jwtUtil);
    }

    @Bean
    public FilterRegistrationBean<Filter> jwtFilterRegistration(JwtAuthenticationFilter jwtAuthenticationFilter) {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        registration.setFilter(jwtAuthenticationFilter);
        // 拦截所有请求
        registration.addUrlPatterns("/*");
        // 设置优先级（数值越小优先级越高）
        registration.setOrder(1);
        return registration;
    }
}
