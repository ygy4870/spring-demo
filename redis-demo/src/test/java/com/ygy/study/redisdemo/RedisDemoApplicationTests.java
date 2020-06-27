package com.ygy.study.redisdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringRunner.class)
@SpringBootTest
class RedisDemoApplicationTests {


    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Test
    void get_and_get() {
        stringRedisTemplate.opsForValue().set("test10:45", "test_value");
        String value = stringRedisTemplate.opsForValue().get("test10:45");
        System.out.println(value);
    }

}
