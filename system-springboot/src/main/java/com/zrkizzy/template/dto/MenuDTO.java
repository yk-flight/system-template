package com.zrkizzy.template.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 菜单数据传输对象
 *
 * @author zhangrongkang
 * @date 2022/8/19
 */
@Data
public class MenuDTO implements Serializable {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 前端访问路径
     */
    private String path;

    /**
     * 组件
     */
    private String component;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 权限标识
     */
    private String permission;

    /**
     * 图标
     */
    private String icon;

    /**
     * 是否启用
     */
    private boolean enabled;

    /**
     * 父Id
     */
    private Integer parentId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 子菜单
     */
    private List<MenuDTO> children;
}
