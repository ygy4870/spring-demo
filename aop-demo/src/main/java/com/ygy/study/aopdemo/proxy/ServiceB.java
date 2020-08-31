package com.ygy.study.aopdemo.proxy;

import org.springframework.stereotype.Component;

@Component
public class ServiceB {

    public void hello1(){
        System.out.println("ServiceB hello1");
    }
    public void hello2(){
        System.out.println("ServiceB hello2");
    }
}
