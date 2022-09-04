package com.zrkizzy.template.controller;

import com.zrkizzy.template.service.UserRoleService;
import com.zrkizzy.template.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户角色关联控制器
 *
 * @author zhangrongkang
 * @date 2022/8/7
 */
@Api(tags = "UserRoleController")
@RestController
@RequestMapping("/user-role")
public class UserRoleController {
    @Resource
    private UserRoleService userRoleService;

    @ApiOperation("根据用户ID获取角色ID")
    @GetMapping("/getRoleByUserId/{id}")
    public Integer getRoleByUserId(@PathVariable Integer id) {
        return userRoleService.getRoleByUserId(id);
    }

    @ApiOperation("更新用户角色")
    @PutMapping("/updateUserRole")
    public Result updateUserRole(Integer userId, Integer roleId) {
        return userRoleService.updateUserRole(userId, roleId);
    }
}
