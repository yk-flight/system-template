package com.zrkizzy.template.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 角色数据传输对象
 *
 * @author zhangrongkang
 * @date 2022/8/20
 */
@Data
@ApiModel("角色数据传输对象")
public class RoleDto {
    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Integer id;

    /**
     * 角色
     */
    @ApiModelProperty("角色")
    private String roleName;

    /**
     * 角色名称
     */
    @ApiModelProperty("角色名称")
    private String roleNameZh;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private Date updateTime;

    /**
     * 角色权限
     */
    @ApiModelProperty("角色权限")
    private List<Integer> permission;
}
