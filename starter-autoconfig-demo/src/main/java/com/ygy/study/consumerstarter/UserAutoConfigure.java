package com.ygy.study.consumerstarter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(UserProfiles.class)
public class UserAutoConfigure {

    @Bean
    @ConditionalOnProperty(prefix = "spring.user", value = "enabled", havingValue = "true")
    public UserClient userClient(UserProfiles userProfiles) {
        System.out.println("-------------------------");
        return new UserClient(userProfiles);
    }

}
