package com.zrkizzy.template.vo.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 菜单查询对象
 *
 * @author zhangrongkang
 * @date 2022/8/19
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "菜单查询对象")
public class MenuVO {

    @ApiModelProperty("菜单名称")
    private String name;

    @ApiModelProperty("菜单状态")
    private Boolean enabled;
}
