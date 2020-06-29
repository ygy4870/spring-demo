package com.ygy.study.consumerstarter;

import org.springframework.boot.context.properties.ConfigurationProperties;

//@Component
@ConfigurationProperties("spring.user")
public class UserProfiles {

    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

}
