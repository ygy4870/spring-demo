package com.ygy.study.aopdemo.proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxy implements MethodInterceptor {

    private Object target;

    public CglibProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        long starttime = System.nanoTime();
        Object invoke = methodProxy.invoke(this.target, objects);
        System.out.println(""+methodProxy.getSuperName()+"执行时长="+(System.nanoTime()-starttime));
        return invoke;
    }

    public static <T> T createProxy(T target) {

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(new CglibProxy(target));
        return (T) enhancer.create();

    }

}
