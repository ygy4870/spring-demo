package com.ygy.study.aopdemo;

import com.ygy.study.aopdemo.proxy.ServiceA;
import com.ygy.study.aopdemo.proxy.ServiceB;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AopDemoApplicationTests {

    @Autowired
    private ServiceA serviceA;
    @Autowired
    private ServiceB serviceB;

    @Test
    void contextLoads() {
        serviceA.say1();
    }

}
