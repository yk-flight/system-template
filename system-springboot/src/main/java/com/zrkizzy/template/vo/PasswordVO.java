package com.zrkizzy.template.vo;

import lombok.Data;

/**
 * 用户更新密码参数对象
 *
 * @author zhangrongkang
 * @date 2022/8/17
 */
@Data
public class PasswordVO {

    /**
     * 旧密码
     */
    private String oldPassword;

    /**
     * 新密码
     */
    private String newPassword;
}
