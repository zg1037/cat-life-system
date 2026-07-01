package com.catlife;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
public class RedisTest {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    void testRedis() {
        redisTemplate.opsForValue().set("test", "Hello Redis");

        String value = redisTemplate.opsForValue().get("test");

        System.out.println("Redis中的值：" + value);
    }
}