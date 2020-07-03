package com.ygy.study.mybatisdemo.dao;

import com.ygy.study.mybatisdemo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserDao {

    void executeSql(User person);
}
