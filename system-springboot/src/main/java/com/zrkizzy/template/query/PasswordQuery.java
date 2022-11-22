package com.zrkizzy.template.query;

import lombok.Getter;
import lombok.Setter;

/**
 * 修改密码数据传输对象
 *
 * @author zhangrongkang
 * @since 2022/11/22
 */
@Getter
@Setter
public class PasswordQuery {

    /**
     * 旧密码
     */
    private String oldPassword;

    /**
     * 新密码
     */
    private String newPassword;

}
