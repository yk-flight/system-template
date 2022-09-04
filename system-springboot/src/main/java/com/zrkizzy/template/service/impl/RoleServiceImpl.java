package com.zrkizzy.template.service.impl;

import com.zrkizzy.template.annotation.LogAnnotation;
import com.zrkizzy.template.dto.RoleDto;
import com.zrkizzy.template.entity.Role;
import com.zrkizzy.template.entity.UserRole;
import com.zrkizzy.template.mapper.MenuMapper;
import com.zrkizzy.template.mapper.RoleMapper;
import com.zrkizzy.template.mapper.UserRoleMapper;
import com.zrkizzy.template.service.RoleService;
import com.zrkizzy.template.utils.BeanCopyUtil;
import com.zrkizzy.template.utils.UserUtil;
import com.zrkizzy.template.vo.Result;
import com.zrkizzy.template.vo.param.RoleVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhangrongkang
 * @date 2022/8/7
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private MenuMapper menuMapper;
    @Resource
    private UserRoleMapper userRoleMapper;

    /**
     * 获取所有角色
     *
     * @return 获取所有角色
     */
    @Override
    public List<RoleDto> getAllRoles() {
        List<Role> roles = roleMapper.selectList(null);
        List<RoleDto> roleDtoList = new ArrayList<>();
        // 设置每一个角色对象
        for (Role role : roles) {
            RoleDto roleDto = BeanCopyUtil.copy(role, RoleDto.class);
            roleDto.setPermission(UserUtil.getPermissionByString(role.getPermission()));
            roleDtoList.add(roleDto);
        }
        return roleDtoList;
    }

    /**
     * 更新角色信息
     *
     * @param roleVO 角色对象
     * @return 前端返回对象
     */
    @Override
    @LogAnnotation(module = "角色模块", description = "更新角色")
    @Transactional(rollbackFor = RuntimeException.class)
    public Result updateRole(RoleVO roleVO) {
        Role role = BeanCopyUtil.copy(roleVO, Role.class);
        // 如果权限列表中含有数据则说明需要更新权限
        if (roleVO.getPermission().size() > 0) {
            role.setPermission(UserUtil.setPermissionToString(roleVO.getPermission()));
        }
        // 定义上一次更新时间
        role.setUpdateTime(new Date());
        // 复制当前角色对象
        int row = roleMapper.updateById(role);
        if (row > 0) {
            return Result.success("角色信息更新成功");
        }
        return Result.error("角色信息更新失败");
    }

    /**
     * 删除指定的角色
     *
     * @param roleId 角色ID
     * @return 前端返回对象
     */
    @Override
    @LogAnnotation(module = "角色模块", description = "删除角色")
    @Transactional(rollbackFor = RuntimeException.class)
    public Result deleteRoleById(Integer roleId) {
        // 删除角色表中对应的数据
        int row = roleMapper.deleteById(roleId);
        if (row > 0) {
            return Result.success("角色删除成功");
        }
        return Result.error("角色删除失败");
    }

    /**
     * 新增角色
     *
     * @param roleVO 角色数据传递对象
     * @return 前端返回对象
     */
    @Override
    @LogAnnotation(module = "角色模块", description = "新增角色")
    @Transactional(rollbackFor = RuntimeException.class)
    public Result insertRole(RoleVO roleVO) {
        Role role = BeanCopyUtil.copy(roleVO, Role.class);
        // 设置角色的初始权限
        role.setPermission("[2,3]");
        // 设置当前角色的创建时间和上一次更新时间
        role.setCreateTime(new Date());
        role.setUpdateTime(new Date());
        int row = roleMapper.insert(role);
        if (row > 0) {
            return Result.success("新增角色成功");
        }
        return Result.error("新增角色失败");
    }

    /**
     * 获取当前登录用户可访问的页面路径
     *
     * @return 可访问的页面路径集合
     */
    @Override
    public List<String> getAccessPath() {
        // 获取当前登录用户的ID
        Integer userId = UserUtil.getCurrentUser().getId();
        // 通过当前用户ID获取到用户对应到角色
        UserRole userRole = userRoleMapper.selectById(userId);
        // 通过角色ID获取到对应到角色对象
        Role role = roleMapper.selectById(userRole.getRoleId());
        List<Integer> permission = UserUtil.getPermissionByString(role.getPermission());
        if (permission == null) {
            return null;
        }
        // 获取到当前用户可以访问的路径并返回
        return menuMapper.selectAccessPath(permission);
    }


}
