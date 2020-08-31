package com.ygy.study.iocdemo.factorybean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * Description: 请描述你的文件
 *
 * @author yangguangying
 * @date 2020-08-31
 * <p>
 * All rights Reserved, Designed www.xiao100.com
 */
@Component
public class MyFactoryBean implements FactoryBean {

    @Override
    public Object getObject() throws Exception {
        return "MyFactoryBean";
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

}
