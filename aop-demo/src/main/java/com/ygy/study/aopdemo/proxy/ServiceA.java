package com.ygy.study.aopdemo.proxy;

public class ServiceA implements IService{

    @Override
    public void say1() {
        System.out.println("ServiceA say1");
    }

    @Override
    public void say2() {
        System.out.println("ServiceA say2");
    }

}
