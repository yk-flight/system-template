package com.zrkizzy.template.vo;

import lombok.Data;

/**
 * 用户登录参数
 *
 * @author zhangrongkang
 * @date 2022/8/7
 */
@Data
public class UserLoginVO {

    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 验证码
     */
    private String code;
}
