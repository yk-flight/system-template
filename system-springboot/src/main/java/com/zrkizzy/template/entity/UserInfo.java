package com.zrkizzy.template.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户信息对象
 *
 * @author zhangrongkang
 * @date 2022/8/15
 */
@Data
@ApiModel("用户信息对象")
@TableName("user_info")
public class UserInfo implements Serializable {
    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户邮箱
     */
    @ApiModelProperty("用户邮箱")
    private String email;

    /**
     * 电话号码
     */
    @ApiModelProperty("电话号码")
    private String phone;

    /**
     * QQ账号
     */
    @ApiModelProperty("QQ账号")
    private String qq;

    /**
     * gitee
     */
    @ApiModelProperty("gitee")
    private String gitee;

    /**
     * git hub
     */
    @ApiModelProperty("git hub")
    private String github;

    /**
     * csdn
     */
    @ApiModelProperty("csdn")
    private String csdn;

    /**
     * leetcode
     */
    @ApiModelProperty("leetcode")
    private String leetcode;

    /**
     * 自我简介
     */
    @ApiModelProperty("自我简介")
    private String description;
}
