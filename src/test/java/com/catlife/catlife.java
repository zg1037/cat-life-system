package com.catlife;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;

@SpringBootTest
class DatabaseTest {

    @Autowired
    private DataSource dataSource;

    @Test
    void testConnection() throws Exception {
        Connection connection = dataSource.getConnection();
        System.out.println("数据库连接成功！");
        System.out.println(connection);
        connection.close();
    }
}