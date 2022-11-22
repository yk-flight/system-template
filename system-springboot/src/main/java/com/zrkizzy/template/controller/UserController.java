package com.zrkizzy.template.controller;

import com.zrkizzy.template.query.AvatarQuery;
import com.zrkizzy.template.query.PasswordQuery;
import com.zrkizzy.template.service.IUserService;
import com.zrkizzy.template.vo.PageVO;
import com.zrkizzy.template.vo.Result;
import com.zrkizzy.template.vo.UserInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 用户模块控制器
 *
 * @author zhangrongkang
 * @date 2022/8/14
 */
@RestController
@Api(tags = "UserController")
@RequestMapping("/user/admin")
public class UserController {
    @Resource
    private IUserService userService;

    @ApiOperation("获取当前登录的用户信息")
    @GetMapping("/getUserById")
    public Result getUserById() {
        return Result.success("", userService.getUserByUserId());
    }

    @ApiOperation("获取用户登录设备")
    @GetMapping("/getUserAgent")
    public String getUserAgent(HttpServletRequest request) {
        return userService.getUserAgent(request);
    }

    @ApiOperation("更新用户密码")
    @PutMapping("/updatePassword")
    public Result updatePassword(@RequestBody PasswordQuery passwordQuery) {
        return userService.updatePassword(passwordQuery);
    }

    @ApiOperation("修改指定用户密码")
    @PutMapping("/updatePasswordById")
    public Result updatePasswordById(Integer userId, String password) {
        return userService.updatePasswordById(userId, password);
    }

    @ApiOperation("获取所有用户")
    @GetMapping("/getUserList")
    public PageVO getUserList(@RequestParam("curPage") Integer curPage, @RequestParam("size") Integer size, @RequestParam("name") String username) {
        return userService.getUserList(curPage, size, username);
    }

    @ApiOperation("新增用户")
    @PostMapping("/addUser")
    public Result addUser(@RequestBody UserInfoVO userInfoVO) {
        return userService.addUser(userInfoVO);
    }

    @ApiOperation("删除用户")
    @DeleteMapping("/deleteUserById/{id}")
    public Result deleteUserById(@PathVariable Integer id) {
        return userService.deleteUserById(id);
    }

    @ApiOperation("用户头像上传")
    @PostMapping("/uploadAvatar")
    public Result uploadAvatar(MultipartFile file) throws IOException {
        return userService.uploadAvatar(file);
    }

    @ApiOperation("更新用户头像")
    @PostMapping("/updateAvatarById")
    public void updateAvatarById(@RequestBody AvatarQuery avatarQuery) {
        userService.updateAvatarById(avatarQuery);
    }

}
