package com.zrkizzy.template.vo.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户个人信息更新对象
 *
 * @author zhangrongkang
 * @date 2022/8/16
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "用户个人信息更新对象")
public class UserInfoVO {
    /**
     * 用户ID
     */
    @ApiModelProperty("用户ID")
    private Integer id;

    /**
     * 角色ID
     */
    @ApiModelProperty("角色ID")
    private Integer roleId;

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String username;

    /**
     * 用户昵称
     */
    @ApiModelProperty("用户昵称")
    private String nickName;

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
