package com.zrkizzy.template.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zrkizzy.template.dto.MenuDTO;
import com.zrkizzy.template.dto.PermissionDTO;
import com.zrkizzy.template.entity.Menu;
import com.zrkizzy.template.entity.Role;
import com.zrkizzy.template.mapper.MenuMapper;
import com.zrkizzy.template.mapper.RoleMapper;
import com.zrkizzy.template.service.MenuService;
import com.zrkizzy.template.utils.BeanCopyUtil;
import com.zrkizzy.template.utils.UserUtil;
import com.zrkizzy.template.vo.MenuVO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangrongkang
 * @date 2022/8/12
 */
@Service
public class MenuServiceImpl implements MenuService {
    @Resource
    private MenuMapper menuMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 根据角色获取菜单列表
     *
     * @return 菜单列表
     */
    @Override
    public List<Menu> getMenuWithRole() {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        // Redis中拉取菜单数据
        List<Menu> roleMenus = BeanCopyUtil.castObjectToList(valueOperations.get("roleMenus"), Menu.class);
        // 查看从Redis中拉取到的菜单数据是否存在
        if (CollectionUtils.isEmpty(roleMenus)) {
            // 首先获取到管理员到权限(管理员具备所有权限)
            Role role = roleMapper.selectById(1);
            // 根据管理员具有到权限ID进行查询
            List<Integer> ids = UserUtil.getPermissionByString(role.getPermission());
            // 如果不存在则从数据库中拉取
            roleMenus = menuMapper.getMenuWithRole(ids);
            // 将从数据库中拉取到的数据添加到Redis中
            valueOperations.set("roleMenus", roleMenus);
        }
        return roleMenus;
    }

    /**
     * 根据菜单名称和菜单状态获取符合条件的所有菜单
     *
     * @param menuVO 菜单参数对象
     * @return 菜单列表
     */
    @Override
    public List<MenuDTO> getAllMenus(MenuVO menuVO) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        // 定义查询条件
        queryWrapper.ne("component", "/");
        if (null != menuVO.getName() && !"".equals(menuVO.getName())) {
            queryWrapper.like("name", menuVO.getName());
        }
        // 如果查询的是禁用的菜单
        if (!menuVO.getEnabled()) {
            queryWrapper.eq("enabled", menuVO.getEnabled());
        }
        // 查询出所有符合条件的菜单集合并转为 MenuDto 集合
        List<MenuDTO> menuList = BeanCopyUtil.copyList(menuMapper.selectList(queryWrapper), MenuDTO.class);
        // 将查询出的菜单设置子集
        menuList = setMenuChildren(1, menuList);
        // 返回菜单集合
        return menuList;
    }

    /**
     * 获取角色权限
     *
     * @return 所有角色权限
     */
    @Override
    public List<PermissionDTO> getAllPermission() {
        List<PermissionDTO> permissionList = roleMapper.getAllPermission();
        List<PermissionDTO> result = new ArrayList<>();
        for (PermissionDTO permissionDto : permissionList) {
            permissionDto.setChildren(setPermissionChildren(permissionDto.getPath(), permissionList));
            if (!CollectionUtils.isEmpty(permissionDto.getChildren())) {
                result.add(permissionDto);
            }
        }
        return result;
    }

    /**
     * 根据查询出的菜单集合封装每个菜单的子菜单
     *
     * @param id       当前menu的父ID
     * @param menuList 所有菜单对象的集合
     * @return 封装好的菜单集合
     */
    private List<MenuDTO> setMenuChildren(Integer id, List<MenuDTO> menuList) {
        // 存储菜单的集合
        List<MenuDTO> childrenList = new ArrayList<>();
        // 遍历所有的菜单集合
        for (MenuDTO menu : menuList) {
            // 如果当前Menu的父ID与传来的ID相同则添加到菜单集合中
            if (menu.getParentId().equals(id)) {
                childrenList.add(menu);
            }
        }
        // 递归获取并设置所有子菜单
        for (MenuDTO menu : childrenList) {
            menu.setChildren(setMenuChildren(menu.getId(), menuList));
        }
        // 如果当前菜单没有子菜单则返回一个空的集合
        if (childrenList.size() == 0) {
            return new ArrayList<>();
        }
        // 将封装好的菜单集合返回
        return childrenList;
    }

    /**
     * 设置权限的子集
     *
     * @param path 当前权限的路径
     * @param permissionList 权限列表
     * @return 设置好权限的集合
     */
    private List<PermissionDTO> setPermissionChildren(String path, List<PermissionDTO> permissionList) {
        List<PermissionDTO> children = new ArrayList<>();

        for (PermissionDTO permissionDto : permissionList) {
            // 如果遍历到本身
            if (path.equals(permissionDto.getPath())) {
                continue;
            }
            // 如果当前权限路径以传来的路径作为开头
            if (permissionDto.getPath().startsWith(path)) {
                children.add(permissionDto);
            }
        }

        // 因为在本系统中只设置了两层，因此不需要再次递归获取权限
        return children;
    }
}
