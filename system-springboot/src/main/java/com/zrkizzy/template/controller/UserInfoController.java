package com.zrkizzy.template.controller;

import com.zrkizzy.template.entity.UserInfo;
import com.zrkizzy.template.service.UserInfoService;
import com.zrkizzy.template.vo.Result;
import com.zrkizzy.template.vo.UserInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户信息前端控制器
 *
 * @author zhangrongkang
 * @date 2022/8/15
 */
@Api(tags = "UserInfoController")
@RestController
public class UserInfoController {
    @Resource
    private UserInfoService userInfoService;

    @ApiOperation("获取用户个人信息")
    @GetMapping("/admin/getCurrentUserInfo")
    public UserInfo getCurrentUserInfo() {
        return userInfoService.getCurrentUserInfo();
    }

    @ApiOperation("更新用户个人信息")
    @PostMapping("/admin/updateUserInfo")
    public Result updateUserInfo(@RequestBody UserInfoVO userInfoVO) {
        return userInfoService.updateUserInfo(userInfoVO);
    }

    @ApiOperation("获取指定用户信息")
    @GetMapping("/admin/getUserInfoById/{id}")
    public UserInfo getUserInfoById(@PathVariable Integer id) {
        return userInfoService.getUserInfoById(id);
    }

    @ApiOperation("修改用户启用状态")
    @PutMapping("/admin/changeUserEnabled/{id}")
    public Result changeUserEnabled(@PathVariable Integer id) {
        return userInfoService.changeUserEnabled(id);
    }
}
