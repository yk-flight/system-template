package com.zrkizzy.template.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zhangrongkang
 * @date 2022/8/6
 */
@RestController
@RequestMapping("/hello")
@Api(tags = "HelloController")
public class HelloController {
    @Resource
    private UserDetailsService userDetailsService;

    @ApiOperation(value = "测试方法")
    @GetMapping("/say")
    public UserDetails sayHello() {
        return userDetailsService.loadUserByUsername("admin");
    }
}
