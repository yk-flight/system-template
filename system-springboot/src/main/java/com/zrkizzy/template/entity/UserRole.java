package com.zrkizzy.template.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户权限关联对象
 *
 * @author zhangrongkang
 * @date 2022/8/7
 */
@Data
@ApiModel("用户角色关联对象")
@TableName("user_role")
public class UserRole implements Serializable {

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    @ApiModelProperty("用户ID")
    private Integer userId;

    /**
     * 角色ID
     */
    @ApiModelProperty("角色ID")
    private Integer roleId;
}
