package com.ygy.study.aopdemo;

import com.ygy.study.aopdemo.proxy.*;
import org.junit.Test;
import org.springframework.cglib.core.DebuggingClassWriter;

public class ProxyTest {

    @Test
    public void jdkProxy() {
        IService jdkProxy = JDKProxy.createProxy(new ServiceA(), IService.class);
        jdkProxy.say1();
        jdkProxy.say2();
    }

    @Test
    public void CglibProxy() {
        IService serviceA = CglibProxy.createProxy(new ServiceA());
        serviceA.say1();
        serviceA.say2();

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

    @Test
    public void name2() {
        String str1 = "hello-axxxxxxhello";
        String hello = "hello";
        String hello2 = "hello2";
        int index = str1.indexOf(hello);
        System.out.println(index);

        System.out.println(hello2 + str1.substring(hello.length()));
    }

    public static void main(String[] args) {
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "E:\\cglib_test");
        IService serviceA = CglibProxy.createProxy(new ServiceA());
        serviceA.say1();
        serviceA.say2();

//        IService service2= JDKProxy.createProxy(new ServiceA(), IService.class);
//        service2.say1();
//        service2.say2();
//
//
//        ServiceB serviceB = CglibProxy.createProxy(new ServiceB());
//        serviceB.hello1();
//        serviceB.hello2();
    }

}
