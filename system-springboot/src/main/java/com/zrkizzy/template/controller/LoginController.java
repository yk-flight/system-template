package com.zrkizzy.template.controller;

import com.zrkizzy.template.query.LoginQuery;
import com.zrkizzy.template.service.IUserService;
import com.zrkizzy.template.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 登录模块控制器
 *
 * @author zhangrongkang
 * @date 2022/8/7
 */
@Api(tags = "LoginController")
@RestController
public class LoginController {
    @Resource
    private IUserService userService;

    @ApiOperation(value = "登录后返回token")
    @PostMapping("/login")
    public Result login(@RequestBody LoginQuery loginQuery, HttpServletRequest request) {
        return userService.login(loginQuery.getUsername(), loginQuery.getPassword(), loginQuery.getCode(), request);
    }

    @ApiOperation(value = "退出登录")
    @PostMapping("/logout")
    public Result logout() {
        return Result.success("退出成功");
    }
}
