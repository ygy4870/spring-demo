package com.ygy.study.aopdemo.bpp;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof Bean4BPP) {
            System.out.println("MyBeanPostProcessor postProcessBeforeInitialization");
        }
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof Bean4BPP) {
            System.out.println("MyBeanPostProcessor postProcessAfterInitialization");
        }
        return null;
    }

}
