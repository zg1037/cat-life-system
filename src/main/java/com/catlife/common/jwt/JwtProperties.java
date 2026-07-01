package com.catlife.common.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    /**
     * 签名密钥
     */
    private String secret;

    /**
     * token 过期时间（毫秒），默认 7 天
     */
    private long expiration = 7 * 24 * 60 * 60 * 1000L;
}
