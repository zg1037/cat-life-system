package com.catlife.common.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 生成 JWT Token
     *
     * @param claims 自定义声明（例如存放 userId）
     * @return token 字符串
     */
    public String generateToken(Map<String, Object> claims) {
        long now = System.currentTimeMillis();

        return Jwts.builder()
                .claims(claims)
                .issuedAt(new Date(now))
                .expiration(new Date(now + jwtProperties.getExpiration()))
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * 解析 JWT Token
     *
     * @param token token 字符串
     * @return Claims（包含所有声明）
     */
    public Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * 校验 token 是否有效
     *
     * @param token token 字符串
     * @return true 有效，false 无效
     */
    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * 从 token 中获取用户 ID
     *
     * @param token token 字符串
     * @return 用户 ID
     */
    public Long getUserIdFromToken(String token) {
        Claims claims = parseToken(token);
        return claims.get("userId", Long.class);
    }

    /**
     * 获取签名密钥
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
    }
}
