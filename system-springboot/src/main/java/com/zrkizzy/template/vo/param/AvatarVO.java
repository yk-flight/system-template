package com.zrkizzy.template.vo.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户头像参数接收对象
 *
 * @author zhangrongkang
 * @date 2022/9/3
 */
@Data
@ApiModel("用户头像参数接收对象")
public class AvatarVO {
    /**
     * 用户头像路径
     */
    @ApiModelProperty("用户头像路径")
    private String avatar;
}
