package com.zrkizzy.template.query;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户登录对象
 *
 * @author zhangrongkang
 * @date 2022/11/13
 */
@Getter
@Setter
public class LoginQuery {

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
