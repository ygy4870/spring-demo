package com.ygy.study.aopdemo;

import com.ygy.study.aopdemo.proxy.ServiceA;
import com.ygy.study.aopdemo.proxy.ServiceB;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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

    @Test
    void test2() {
        Method[] declaredMethods = ServiceA.class.getDeclaredMethods();
        for (int i = 0; i < declaredMethods.length; i++) {
            declaredMethods[i].setAccessible(true);
            try {
                declaredMethods[i].invoke(serviceA);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
