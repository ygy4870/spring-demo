package com.ygy.study.aopdemo.bbp;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * Description: 请描述你的文件
 *
 * @author yangguangying
 * @date 2020-08-24
 * <p>
 * All rights Reserved, Designed www.xiao100.com
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof Bean4BBP) {
            System.out.println("MyBeanPostProcessor postProcessBeforeInitialization");
        }
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof Bean4BBP) {
            System.out.println("MyBeanPostProcessor postProcessAfterInitialization");
        }
        return null;
    }

}
