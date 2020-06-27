package com.ygy.study.redisdemo.serializer;

import com.ygy.study.redisdemo.model.UserSerializable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * RedisTemplate
 *
 * 1、默认使用序列化：JdkSerializationRedisSerializer
 * 2、对象需要实现序列化接口，如：public class User implements Serializable {
 *
 */
@SpringBootTest
public class DefaultSerializerTest {

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 默认使用：JdkSerializationRedisSerializer
     *
     *
     *  RedisTemplate.java
     *
     *  @Override
     * 	public void afterPropertiesSet() {
     *
     * 		super.afterPropertiesSet();
     *
     * 		boolean defaultUsed = false;
     *
     * 		if (defaultSerializer == null) {
     *
     * 			defaultSerializer = new JdkSerializationRedisSerializer(
     * 					classLoader != null ? classLoader : this.getClass().getClassLoader());
     * 		}
     */
    @Test
    void default_serializer() {
        redisTemplate.opsForValue().set("default_serializer", "default_serializer_value");
        Object default_serializer = redisTemplate.opsForValue().get("default_serializer");
        System.out.println(default_serializer);
    }

    /**
     * 使用默认的JdkSerializationRedisSerializer序列化方式，对象需要实现序列化，
     * 否则会异常：
     *      Caused by: java.lang.IllegalArgumentException: DefaultSerializer requires a Serializable payload but received an object of type [com.ygy.study.redisdemo.model.User]
     */
    @Test
    void test_obj_imp_serializer() {
        redisTemplate.opsForValue().set("user_serializer", new UserSerializable("ygy", 20));
        UserSerializable user = (UserSerializable)redisTemplate.opsForValue().get("user_serializer");
        System.out.println(user);
    }

}
