package com.ygy.study.iocdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class IocDemoApplicationTests {

    @Autowired
    ApplicationContext applicationContext;

    @Test
    void name() {
        System.out.println(applicationContext);
    }

}
