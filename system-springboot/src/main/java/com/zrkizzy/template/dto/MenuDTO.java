package com.zrkizzy.template.dto;

import lombok.Data;

/**
 * 菜单查询对象
 *
 * @author zhangrongkang
 * @date 2022/8/19
 */
@Data
public class MenuDTO {

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单状态
     */
    private Boolean enabled;

}
