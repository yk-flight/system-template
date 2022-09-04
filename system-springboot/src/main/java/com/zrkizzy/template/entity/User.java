package com.zrkizzy.template.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户实体类
 *
 * @author zhangrongkang
 * @date 2022/8/6
 */
@Data
@TableName("user")
@ApiModel("用户对象")
public class User implements UserDetails, Serializable {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("ID")
    private Integer id;

    /**
     * 用户昵称
     */
    @ApiModelProperty("用户昵称")
    private String nickName;

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String username;

    /**
     * 用户密码
     */
    @ApiModelProperty("用户密码")
    private String password;

    /**
     * 用户头像
     */
    @ApiModelProperty("用户头像")
    private String avatar;

    /**
     * 用户是否启用
     */
    @ApiModelProperty("用户是否启用")
    @Getter(AccessLevel.NONE)
    private Boolean enabled;

    /**
     * 登录IP
     */
    @ApiModelProperty("登录IP")
    private String ipAddress;

    /**
     * IP属地
     */
    @ApiModelProperty("IP属地")
    private String ipSource;

    /**
     * 上一次登录时间
     */
    @ApiModelProperty("上一次登录时间")
    private Date lastLoginTime;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private Date updateTime;

    /**
     * 用户具有的角色
     */
    @ApiModelProperty(value = "角色")
    @TableField(exist = false)
    private List<Role> roles;

    /**
     * 用户具有的菜单权限
     */
    @ApiModelProperty(value = "菜单权限")
    @TableField(exist = false)
    private List<String> permission;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toList());
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
