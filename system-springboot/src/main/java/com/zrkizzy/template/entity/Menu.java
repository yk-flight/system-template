package com.zrkizzy.template.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 菜单实体类
 *
 * @author zhangrongkang
 * @date 2022/8/12
 */
@Data
@ApiModel("菜单对象")
@TableName("menu")
public class Menu implements Serializable {

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 后端访问路径
     */
    @ApiModelProperty("后端访问路径")
    private String url;

    /**
     * 前端访问路径
     */
    @ApiModelProperty("前端访问路径")
    private String path;

    /**
     * 组件
     */
    @ApiModelProperty("组件")
    private String component;

    /**
     * 菜单权限
     */
    @ApiModelProperty("菜单权限")
    private String permission;

    /**
     * 菜单名称
     */
    @ApiModelProperty("菜单名称")
    private String name;

    /**
     * 图标
     */
    @ApiModelProperty("图标")
    private String icon;

    /**
     * 是否需要权限
     */
    @ApiModelProperty("是否需要权限")
    private boolean requireAuth;

    /**
     * 父Id
     */
    @ApiModelProperty("父Id")
    private Integer parentId;

    /**
     * 是否启用
     */
    @ApiModelProperty("是否启用")
    private boolean enabled;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * 子菜单
     */
    @ApiModelProperty(value = "子菜单")
    @TableField(exist = false)
    private List<Menu> children;

    /**
     * 角色列表
     */
    @ApiModelProperty(value = "角色列表")
    @TableField(exist = false)
    private List<Role> roles;
}
