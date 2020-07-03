package com.ygy.study.mybatisdemo.service;

import com.ygy.study.mybatisdemo.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Transactional
    public Long add() {
        return 1L;
    }

}
