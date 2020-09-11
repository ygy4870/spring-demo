package com.ygy.study.aopdemo;

import com.alibaba.fastjson.JSON;
import com.ygy.study.aopdemo.proxy.*;
import org.junit.Test;

import java.util.List;

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

    @Test
    public void name2() {
        String str1 = "hello-axxxxxxhello";
        String hello = "hello";
        String hello2 = "hello2";
        int index = str1.indexOf(hello);
        System.out.println(index);

        System.out.println(hello2 + str1.substring(hello.length()));
    }


    @Test
    public void dsfdsfds() {

        String refundEcEmReasons ="[\"散热温热\",\"条约他问我\"]";

        List<String> list = JSON.parseArray(refundEcEmReasons, String.class);

        System.out.println(list);
    }

}
