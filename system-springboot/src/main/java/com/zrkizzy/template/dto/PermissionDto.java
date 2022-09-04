package com.zrkizzy.template.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 权限数据传输对象
 *
 * @author zhangrongkang
 * @date 2022/8/20
 */
@Data
@ApiModel("权限数据传输对象")
public class PermissionDto {

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Integer id;

    /**
     * 路径权限
     */
    @ApiModelProperty("路径权限")
    private String path;

    /**
     * 权限名称
     */
    @ApiModelProperty("权限名称")
    private String name;

    /**
     * 图标
     */
    @ApiModelProperty("菜单图标")
    private String icon;

    /**
     * 权限子项
     */
    @ApiModelProperty("权限子项")
    private List<PermissionDto> children;
}
