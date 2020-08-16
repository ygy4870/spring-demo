package com.ygy.study.aopdemo;

import com.ygy.study.aopdemo.proxy.*;
import org.junit.Test;

public class ProxyTest {

    @Test
    public void jdkProxy() {
        IService jdkProxy = JDKProxy.createProxy(new ServiceA(), IService.class);
        jdkProxy.say1();
        jdkProxy.say2();
    }

    @Test
    public void CglibProxy() {
        IService cglibProxy = CglibProxy.createProxy(new ServiceA());
        cglibProxy.say1();
        cglibProxy.say2();

        ServiceB serviceB = CglibProxy.createProxy(new ServiceB());
        serviceB.hello1();
        serviceB.hello2();


    }

    @Test
    public void name() {
        String str1 = "hello";
        String str2 = "he" + new String("llo");
        System.out.println(str1==str2);
        System.out.println(str1.equals(str2));
    }
}
