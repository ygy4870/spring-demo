package com.ygy.study.starterautoconfigdemo.rest;

import com.ygy.study.consumerstarter.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private UserClient userClient;

    @GetMapping("/hello")
    public String hello() {

        return "hello" + userClient.getUserProfiles().getName();
    }

}
