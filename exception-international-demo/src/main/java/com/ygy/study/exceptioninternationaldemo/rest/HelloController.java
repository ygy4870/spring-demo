package com.ygy.study.exceptioninternationaldemo.rest;

import com.ygy.study.exceptioninternationaldemo.common.Constants;
import com.ygy.study.exceptioninternationaldemo.common.EduResponseEntity;
import com.ygy.study.exceptioninternationaldemo.exception.BusinessException;
import com.ygy.study.exceptioninternationaldemo.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {


    @Autowired
    private HelloService helloService;

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {

        return EduResponseEntity.ok("hello exception test");
    }

    @GetMapping("/testBusiness")
    public ResponseEntity<String> testBusiness() {

        return EduResponseEntity.buildBusinessError(Constants.Test.UidEmpty);
    }

    @GetMapping("/testException")
    public ResponseEntity<String> testException() {

        throw new RuntimeException("test throw exception");
    }

    @GetMapping("/testService")
    public ResponseEntity<String> testService() throws BusinessException {

        helloService.testException();

        return EduResponseEntity.ok("suc");
    }





}
