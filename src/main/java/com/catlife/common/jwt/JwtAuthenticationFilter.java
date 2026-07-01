package com.catlife.common.jwt;

import com.catlife.common.result.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * JWT 认证过滤器
 * 拦截需要认证的请求，校验 token 是否有效
 */
public class JwtAuthenticationFilter implements Filter {

    /**
     * 白名单路径（不需要 token 即可访问）
     */
    private static final List<String> WHITE_LIST = Arrays.asList(
            "/user/login",
            "/user/register",
            "/doc.html",
            "/swagger-ui",
            "/v3/api-docs",
            "/swagger-resources"
    );

    private final JwtUtil jwtUtil;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 1. 获取请求路径
        String requestURI = request.getRequestURI();

        // 2. 白名单路径直接放行
        if (isWhiteList(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. 从请求头中获取 token
        String token = request.getHeader("Authorization");

        // 4. 如果没有 token，返回 401
        if (!StringUtils.hasText(token)) {
            writeUnauthorized(response, "未登录，请先登录");
            return;
        }

        // 5. 去除 "Bearer " 前缀
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        // 6. 校验 token
        if (!jwtUtil.validateToken(token)) {
            writeUnauthorized(response, "token 无效或已过期，请重新登录");
            return;
        }

        // 7. token 有效，放行
        filterChain.doFilter(request, response);
    }

    /**
     * 判断是否为白名单路径
     */
    private boolean isWhiteList(String requestURI) {
        for (String whitePath : WHITE_LIST) {
            if (requestURI.startsWith(whitePath)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 返回 401 未授权响应
     */
    private void writeUnauthorized(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        Result<?> result = Result.error(401, message);
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}
