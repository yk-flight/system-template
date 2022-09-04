package com.zrkizzy.template.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户角色对象
 *
 * @author zhangrongkang
 * @date 2022/8/7
 */
@Data
@ApiModel("角色对象")
@TableName("role")
public class Role implements Serializable {

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @TableId(type = IdType.AUTO)
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
     * 角色权限
     */
    @ApiModelProperty("角色权限")
    private String permission;

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
}
