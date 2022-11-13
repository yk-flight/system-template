package com.zrkizzy.template.dto;

import lombok.Data;

import java.util.List;

/**
 * 权限数据传输对象
 *
 * @author zhangrongkang
 * @date 2022/8/20
 */
@Data
public class PermissionDTO {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 路径权限
     */
    private String path;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 图标
     */
    private String icon;

    /**
     * 权限子项
     */
    private List<PermissionDTO> children;
}
