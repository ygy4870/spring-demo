package com.ygy.study.redisdemo.dao;

import com.ygy.study.redisdemo.model.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

    public User getByName(String name) {
        return new User(name, 30);
    }

    public User add(User user) {
        return  user;
    }

}
