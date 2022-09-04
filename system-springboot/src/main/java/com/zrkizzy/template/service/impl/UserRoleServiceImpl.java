package com.zrkizzy.template.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zrkizzy.template.entity.UserRole;
import com.zrkizzy.template.mapper.UserRoleMapper;
import com.zrkizzy.template.service.UserRoleService;
import com.zrkizzy.template.vo.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author zhangrongkang
 * @date 2022/8/7
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Resource
    private UserRoleMapper userRoleMapper;

    /**
     * 根据用户ID获取角色ID
     *
     * @param userId 用户ID
     * @return 角色ID
     */
    @Override
    public Integer getRoleByUserId(Integer userId) {
        return userRoleMapper.selectOne(new QueryWrapper<UserRole>().eq("user_id", userId)).getRoleId();
    }

    /**
     * 更新用户的角色
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     * @return 前端响应独享
     */
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Result updateUserRole(Integer userId, Integer roleId) {
        UserRole userRole = userRoleMapper.selectById(userId);
        userRole.setRoleId(roleId);
        int count = userRoleMapper.updateById(userRole);
        if (count > 0) {
            return Result.success("更新成功");
        }
        return Result.error("更新失败");
    }
}
