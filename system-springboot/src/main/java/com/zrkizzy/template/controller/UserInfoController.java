package com.zrkizzy.template.controller;

import com.zrkizzy.template.dto.UserInfoDTO;
import com.zrkizzy.template.entity.UserInfo;
import com.zrkizzy.template.service.IUserInfoService;
import com.zrkizzy.template.vo.Result;
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
    private IUserInfoService IUserInfoService;

    @ApiOperation("获取用户个人信息")
    @GetMapping("/admin/getCurrentUserInfo")
    public UserInfo getCurrentUserInfo() {
        return IUserInfoService.getCurrentUserInfo();
    }

    @ApiOperation("更新用户个人信息")
    @PostMapping("/admin/updateUserInfo")
    public Result updateUserInfo(@RequestBody UserInfoDTO userInfoDTO) {
        return IUserInfoService.updateUserInfo(userInfoDTO);
    }

    @ApiOperation("获取指定用户信息")
    @GetMapping("/admin/getUserInfoById/{id}")
    public UserInfo getUserInfoById(@PathVariable Integer id) {
        return IUserInfoService.getUserInfoById(id);
    }

    @ApiOperation("修改用户启用状态")
    @PutMapping("/admin/changeUserEnabled/{id}")
    public Result changeUserEnabled(@PathVariable Integer id) {
        return IUserInfoService.changeUserEnabled(id);
    }
}
