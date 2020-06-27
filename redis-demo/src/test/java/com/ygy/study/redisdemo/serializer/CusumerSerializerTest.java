package com.ygy.study.redisdemo.serializer;

import com.ygy.study.redisdemo.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 自定义序列化方式
 *
 * 1、RedisConfig.java  ---------配置
 *
 */
@SpringBootTest
public class CusumerSerializerTest {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

        @Test
        void test_obj_json_serializer() {
            redisTemplate.opsForValue().set("user_json_serializer", new User("ygy", 20));
            User user = (User)redisTemplate.opsForValue().get("user_json_serializer");
            System.out.println(user);
        }

}
