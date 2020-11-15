package com.ygy.study.aopdemo;


import com.ygy.study.aopdemo.proxy.CglibProxy;
import com.ygy.study.aopdemo.proxy.CglibSimple;
import org.junit.Test;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class CglibTest {

    /**
     * 哪些连接点可以作为切点
     */
    @Test
    public void test_point_aspect() {
        CglibSimple sample = CglibProxy.createProxy(new CglibSimple());
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
    }

    @Test
    public void multiple_methodInterceptor() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(CglibSimple.class);


        List<Callback> methodInterceptorList = new ArrayList<>();

        methodInterceptorList.add(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println(method.getName() + "----------------start--------1-----------");
                Object invoke = methodProxy.invokeSuper(o, objects);
                System.out.println(method.getName() + "-----------------end----------1----------");
                return invoke;
            }
        });
        methodInterceptorList.add(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println(methodProxy.getSignature() + "----------------start--------2-----------");
                Object invoke = methodProxy.invokeSuper(o, objects);
                System.out.println(methodProxy.getSignature() + "-----------------end---------2-----------");
                return invoke;
            }
        });
        enhancer.setCallbacks(methodInterceptorList.toArray(new Callback[methodInterceptorList.size()]));
        enhancer.setCallbackFilter((method) ->{
            String methodName = method.getName();
            if ("testPublic".equals(methodName)) {
                return 1; // testPublic()方法使用callbacks[1]对象拦截。
            }
            return 0;   //其他方法使用callbacks[0]对象拦截。
        });



        CglibSimple cglibSimple = (CglibSimple) enhancer.create();
        cglibSimple.testPublic();

        /**
         * public String toString() {
         *         return getClass().getName() + "@" + Integer.toHexString(hashCode());
         *     }
         *     为什么 toString 方法内部调用hashCode()，hashCode()也会被拦截
         */
        cglibSimple.toString();
        /**
         * public void testPublic2() {
         *         System.out.println("-------testPublic2------");
         *         testPublic();
         *     }
         *     内部调用testPublic();，也被拦截？？？？？？？？怎么解析
         */
        cglibSimple.testPublic2();
    }

    @Test
    public void methodInterceptor_Nested() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(CglibSimple.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println(method.getName() + "----------------start--------1-----------");
                Object invoke = methodProxy.invokeSuper(o, objects);
                System.out.println(method.getName() + "-----------------end----------1----------");
                return invoke;
            }
        });
        CglibSimple cglibSimple = (CglibSimple) enhancer.create();
        cglibSimple.testPublic();
    }


}
