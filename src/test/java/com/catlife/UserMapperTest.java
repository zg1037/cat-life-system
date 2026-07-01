package com.catlife;

import com.catlife.entity.User;
import com.catlife.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void testSelectByUsername() {

        User user = userMapper.selectByUsername("admin");

        System.out.println(user);

    }
}