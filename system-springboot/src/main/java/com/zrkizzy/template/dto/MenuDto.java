package com.zrkizzy.template.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel("菜单数据传输对象")
public class MenuDto implements Serializable {

    /**
     * 菜单ID
     */
    @ApiModelProperty("菜单ID")
    private Integer id;

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
     * 菜单名称
     */
    @ApiModelProperty("菜单名称")
    private String name;

    /**
     * 权限标识
     */
    @ApiModelProperty("权限标识")
    private String permission;

    /**
     * 图标
     */
    @ApiModelProperty("图标")
    private String icon;

    /**
     * 是否启用
     */
    @ApiModelProperty("是否启用")
    private boolean enabled;

    /**
     * 父Id
     */
    @ApiModelProperty("父Id")
    private Integer parentId;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * 子菜单
     */
    @ApiModelProperty(value = "子菜单")
    private List<MenuDto> children;
}
