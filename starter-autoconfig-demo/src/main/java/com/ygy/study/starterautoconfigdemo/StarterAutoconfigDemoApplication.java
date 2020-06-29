package com.ygy.study.starterautoconfigdemo;

import com.ygy.study.consumerstarter.EnableUserClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableUserClient
@SpringBootApplication
public class StarterAutoconfigDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(StarterAutoconfigDemoApplication.class, args);
    }

}
