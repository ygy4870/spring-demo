package com.ygy.study.redisdemo.cache;

import com.ygy.study.redisdemo.dao.UserCacheDao;
import com.ygy.study.redisdemo.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisCacheTest {

    @Autowired
    UserCacheDao userCacheDao;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;


    @Test
    void redisCacheTest() {
        User zhangsan = userCacheDao.getByName("zhangsan");
        System.out.println(zhangsan);

        User zhangsan2 = (User)redisTemplate.opsForValue().get("user::zhangsan");
        System.out.println(zhangsan2);

    }


}
