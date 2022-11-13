package com.zrkizzy.template.service;

import com.zrkizzy.template.vo.Result;

/**
 * @author zhangrongkang
 * @date 2022/8/7
 */
public interface IUserRoleService {
    /**
     * 根据用户ID获取角色ID
     *
     * @param userId 用户ID
     * @return 角色ID
     */
    Integer getRoleByUserId(Integer userId);

    /**
     * 更新用户的角色
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     * @return 前端响应独享
     */
    Result updateUserRole(Integer userId, Integer roleId);
}
