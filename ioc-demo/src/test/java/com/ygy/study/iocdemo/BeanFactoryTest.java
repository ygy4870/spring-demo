package com.ygy.study.iocdemo;

import com.ygy.study.iocdemo.beanfactory.annotation.ABean;
import com.ygy.study.iocdemo.beanfactory.annotation.AbcBean;
import com.ygy.study.iocdemo.beanfactory.xml.TestXmlBean;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanFactoryTest {

    @Test
    void annotation_test() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.ygy.study.iocdemo.beanfactory");

        /**
         * 根据 beanName 获取bean，不设置指定名称时@Component("aBean")，
         * ABean.class 默认很神奇，是“ABean”而不是首字母小写的“aBean”
         * AbcBean.class 却是默认首字母小写“abcBean”
         * TODO 不知道为什么，可能是bug
         */
//        System.out.println(context.getBean("aBean"));
        System.out.println(context.getBean("ABean"));
        System.out.println(context.getBean(ABean.class));

        System.out.println(context.getBean("abcBean"));
//        System.out.println(context.getBean("AbcBean"));
        System.out.println(context.getBean(AbcBean.class));
    }

    @Test
    void xml_test() {
        ClassPathXmlApplicationContext xmlApplicationContext = new ClassPathXmlApplicationContext("xml-application.xml");
        System.out.println(xmlApplicationContext.getBean(TestXmlBean.class));
    }

}
