package com.zrkizzy.template.dto;

import lombok.Data;

/**
 * 用户个人信息更新对象
 *
 * @author zhangrongkang
 * @date 2022/8/16
 */
@Data
public class UserInfoDTO {

    /**
     * 用户ID
     */
    private Integer id;

    /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * QQ账号
     */
    private String qq;

    /**
     * gitee
     */
    private String gitee;

    /**
     * git hub
     */
    private String github;

    /**
     * csdn
     */
    private String csdn;

    /**
     * leetcode
     */
    private String leetcode;

    /**
     * 自我简介
     */
    private String description;

}
