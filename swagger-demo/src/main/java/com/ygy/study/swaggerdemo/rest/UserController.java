package com.ygy.study.swaggerdemo.rest;

import com.ygy.study.swaggerdemo.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "用户相关接口", description = "提供用户相关的 Rest API")
public class UserController {

    @ApiOperation("新增用户接口")
    @PostMapping("/add")
    public boolean addUser(@RequestBody User user) {
        return false;
    }

    @ApiOperation("获取用户接口")
    @GetMapping("/get/{id}")
    public User getById(@PathVariable("id") int id) {
        return new User();
    }

    @ApiOperation("修改用户接口")
    @PutMapping("/update")
    public boolean update(@RequestBody User user) {
        return true;
    }

    @ApiOperation("删除用户接口")
    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable("id") int id) {
        return true;
    }

}
