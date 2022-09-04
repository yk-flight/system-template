package com.zrkizzy.template.vo.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户更新密码参数对象
 *
 * @author zhangrongkang
 * @date 2022/8/17
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "用户更新密码对象")
public class PasswordVO {

    /**
     * 旧密码
     */
    @ApiModelProperty("旧密码")
    private String oldPassword;

    /**
     * 新密码
     */
    @ApiModelProperty("新密码")
    private String newPassword;
}
