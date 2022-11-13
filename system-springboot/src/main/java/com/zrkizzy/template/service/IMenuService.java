package com.zrkizzy.template.service;

import com.zrkizzy.template.dto.MenuDTO;
import com.zrkizzy.template.dto.PermissionDTO;
import com.zrkizzy.template.entity.Menu;
import com.zrkizzy.template.vo.MenuVO;

import java.util.List;

/**
 * @author zhangrongkang
 * @date 2022/8/12
 */
public interface IMenuService {
    /**
     * 根据角色获取菜单列表
     *
     * @return 菜单列表
     */
    List<Menu> getMenuWithRole();

    /**
     * 根据菜单名称和菜单状态获取符合条件的所有菜单
     *
     * @param menuVO 菜单参数对象
     * @return 菜单列表
     */
    List<MenuDTO> getAllMenus(MenuVO menuVO);

    /**
     * 获取所有权限
     *
     * @return 所有角色权限
     */
    List<PermissionDTO> getAllPermission();
}