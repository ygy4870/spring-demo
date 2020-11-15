package com.ygy.study.aopdemo;


import com.ygy.study.aopdemo.proxy.CglibSimple;
import org.junit.Test;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CglibTest {

    /**
     * 哪些连接点可以作为切点
     */
    @Test
    public void test_point_aspect() {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(CglibSimple.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                System.out.println("before method run...");
                Object result = proxy.invokeSuper(obj, args);
                System.out.println("after method run...");
                System.out.println("");
                return result;
            }
        });
        CglibSimple sample = (CglibSimple) enhancer.create();
        Method[] declaredMethods = CglibSimple.class.getDeclaredMethods();
        for (int i = 0; i < declaredMethods.length; i++) {
            try {
                declaredMethods[i].setAccessible(true);
                declaredMethods[i].invoke(sample);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
//        try {
//            System.in.read();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

}
