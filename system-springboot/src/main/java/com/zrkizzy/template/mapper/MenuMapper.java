package com.zrkizzy.template.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zrkizzy.template.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhangrongkang
 * @date 2022/8/12
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
    /**
     * 根据角色获取菜单列表
     *
     * @param ids 菜单ID列表
     * @return 菜单列表
     */
    List<Menu> getMenuWithRole(@Param("ids") List<Integer> ids );

    /**
     * 获取到当前角色可以访问的路径
     *
     * @param ids 角色权限
     * @return 当前角色可以访问的路径
     */
    List<String> selectAccessPath(@Param("ids") List<Integer> ids);

    /**
     * 获取用户可以访问的权限标识
     *
     * @param ids 当前用户具有的权限数组
     * @return 用户可以访问的权限标识集合
     */
    List<String> selectPermission(@Param("ids") List<Integer> ids);
}
