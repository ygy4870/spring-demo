package com.ygy.study.iocdemo;


import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class FactoryBeanTest {

    /**
     * 实现了 MyFactoryBean 的 bean, 这里 MyFactoryBean implements FactoryBean
     * 通过容器，根据beanName(如：myFactoryBean)获取bean，返回的不是MyFactoryBean而是MyFactoryBean.getObject() 的返回值
     */
    @Test
    public void test() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.ygy.study.iocdemo.factorybean");
        Object myFactoryBean = context.getBean("myFactoryBean");
        System.out.println(myFactoryBean.getClass());
    }

    @Test
    void name() {
        String lastLevel = "00010110";
        String nextOrgLevel = String.valueOf((Integer.valueOf(lastLevel.substring(lastLevel.length() - 4, lastLevel.length())) + 1));
        nextOrgLevel = "0000".substring(0, 4 - nextOrgLevel.length()) + nextOrgLevel;
        System.out.println(nextOrgLevel);
    }
}
