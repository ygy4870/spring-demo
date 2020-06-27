package com.ygy.study.redisdemo.dao;

import com.ygy.study.redisdemo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Repository;

@EnableCaching
@CacheConfig(cacheNames = "user")
@Repository
public class UserCacheDao {

    @Autowired
    UserDao userDao;

    /**
     *      缓存key为：@CacheConfig(cacheNames = "user") + @Cacheable(key = "#name")
     *      比如：user::name
     * @param name
     * @return
     */
    @Cacheable(key = "#name")
    public User getByName(String name) {
        return userDao.getByName(name);
    }

    @CachePut(key = "#user.name", condition = "#user.age ge 20")
    public User add(User user) {
        return userDao.add(user);
    }

    @CachePut(key = "#user.name")
    public User updateUser(User user) {
        return userDao.add(user);
    }

    /**
     * 根据key删除缓存区中的数据
     */
    @CacheEvict(key = "#name")
    public void deleteUser(String name) {

    }

}
