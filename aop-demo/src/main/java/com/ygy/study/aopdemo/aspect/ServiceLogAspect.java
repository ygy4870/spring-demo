package com.ygy.study.aopdemo.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class ServiceLogAspect {

    @Pointcut("execution(* com.ygy.study.aopdemo.proxy.ServiceA.*())")
    public void log(){}

    @Before("log()")
    public void bb() {
        System.out.println("ServiceLogAspect------------");
    }

}
