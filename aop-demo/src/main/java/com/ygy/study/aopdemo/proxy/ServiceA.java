package com.ygy.study.aopdemo.proxy;

import org.springframework.stereotype.Component;

@Component
public class ServiceA implements IService{

    @Override
    public void say1() {
        System.out.println("ServiceA say1");
    }

    @Override
    public void say2() {
        System.out.println("ServiceA say2");
    }

    private void say3() {
        System.out.println("ServiceA say3");
    }

    public final void say4() {
        System.out.println("ServiceA say4");
    }

    public static void say5() {
        System.out.println("ServiceA say4");
    }

}
