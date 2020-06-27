package com.ygy.study.redisdemo.redisconfig;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        // 创建 RedisTemplate 对象
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // 设置 RedisConnection 工厂。😈 它就是实现多种 Java Redis 客户端接入的秘密工厂。感兴趣的胖友，可以自己去撸下。
        template.setConnectionFactory(factory);

        // 使用 String 序列化方式，序列化 KEY 。
        template.setKeySerializer(RedisSerializer.string());

        // 使用 JSON 序列化方式（库是 Jackson ），序列化 VALUE 。
//        template.setValueSerializer(RedisSerializer.json());


        /**
         *  序列化，其实就是定义：对象和字节数组的相互转换
         *
         *  public interface RedisSerializer<T> {
         *
         * 	    @Nullable
         * 	    byte[] serialize(@N ullable T t) throws SerializationException;
         *
         * 	    @Nullable
         * 	    T deserialize( @Nullable byte[] bytes) throws SerializationException;
         */
        template.setValueSerializer(new GenericFastJsonRedisSerializer());// 使用 fastJson 序列化


        return template;
    }


}
