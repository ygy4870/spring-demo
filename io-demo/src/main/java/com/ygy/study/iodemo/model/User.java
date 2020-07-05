package com.ygy.study.iodemo.model;

import com.alibaba.excel.annotation.ExcelProperty;

public class User {

    @ExcelProperty(value ="姓名",index = 1)
    private String name;
    @ExcelProperty(value ="年龄",index = 2)
    private Integer age;
    @ExcelProperty(value ="性别",index = 3)
    private Integer sex;


    public User(){}
    public User(String name, Integer age, Integer sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

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

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                '}';
    }
}
