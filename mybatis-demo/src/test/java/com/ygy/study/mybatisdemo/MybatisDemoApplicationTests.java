package com.ygy.study.mybatisdemo;

import com.ygy.study.mybatisdemo.dao.UserDao;
import com.ygy.study.mybatisdemo.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

@SpringBootTest
class MybatisDemoApplicationTests {

    @Autowired
    DataSource mysqlDataSource;

    @Autowired
    UserDao userDao;

    @Test
    void contextLoads() {
        System.out.println("---------------");

        User user = new User();
        user.setName("555");
        user.setAge(10);
        userDao.executeSql(user);
    }

}
