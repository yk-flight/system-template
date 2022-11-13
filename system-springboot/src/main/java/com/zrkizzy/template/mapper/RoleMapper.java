package com.zrkizzy.template.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zrkizzy.template.dto.PermissionDTO;
import com.zrkizzy.template.entity.Role;

import java.util.List;

/**
 * @author zhangrongkang
 * @date 2022/8/7
 */
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 通过用户ID获取对应的用户权限
     *
     * @param userId 用户ID
     * @return 当前用户的权限集合
     */
    List<Role> getRoles(Integer userId);

    /**
     * 获取所有权限
     *
     * @return 权限列表
     */
    List<PermissionDTO> getAllPermission();

    /**
     * 通过UserId获取到对应到角色
     *
     * @param userId 用户ID
     * @return 角色权限
     */
    String selectByUserId(Integer userId);
}

