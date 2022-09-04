package com.zrkizzy.template.vo.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户登录参数
 *
 * @author zhangrongkang
 * @date 2022/8/7
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "用户登录对象", description = "")
public class UserLoginVO {
    @ApiModelProperty(value = "用户名", required = true)
    private String username;
    @ApiModelProperty(value = "密码", required = true)
    private String password;
    @ApiModelProperty(value = "验证码", required = true)
    private String code;
}
