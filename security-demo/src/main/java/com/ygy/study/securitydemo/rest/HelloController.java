package com.ygy.study.securitydemo.rest;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
//    @PreAuthorize("@")
    public String hello(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(principal);
        return "222222222222";
    }
}
