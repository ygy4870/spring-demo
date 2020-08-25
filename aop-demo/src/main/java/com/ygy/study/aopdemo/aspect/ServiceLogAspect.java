package com.ygy.study.aopdemo.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Description: 请描述你的文件
 *
 * @author yangguangying
 * @date 2020-08-25
 * <p>
 * All rights Reserved, Designed www.xiao100.com
 */
@Component
@Aspect
public class ServiceLogAspect {

    @Pointcut("execution(* com.ygy.study.aopdemo.proxy.ServiceA.say1())")
    public void log(){}

    @Before("log()")
    public void bb() {
        System.out.println("ServiceLogAspect------------");
    }

}
