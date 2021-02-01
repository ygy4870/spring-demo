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

    public static void hello3(){
        System.out.println("ServiceB hello3");
    }

    public final void hello4(){
        System.out.println("ServiceB hello4");
    }

    private void hello5() {
        System.out.println("ServiceB hello5");
    }

    protected void hello6() {
        System.out.println("ServiceB hello6");
    }

}
