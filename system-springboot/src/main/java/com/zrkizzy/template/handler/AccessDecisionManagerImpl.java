package com.zrkizzy.template.handler;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 权限控制：判断用户角色
 *
 * @author zhangrongkang
 * @date 2022/8/7
 */
@Component
public class AccessDecisionManagerImpl implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        for (ConfigAttribute configAttribute : collection) {
            // 当前url所需要的角色
            String needRole = configAttribute.getAttribute();
            // 判断角色是否为登录即可访问的角色，在FilterInvocationSecurityMetadataSourceImpl中设置的
            if ("ROLE_LOGIN".equals(needRole)) {
                // 判断是否登录
                if (authentication instanceof AnonymousAuthenticationToken) {
                    throw new AccessDeniedException("当前未登录，请登录！");
                } else {
                    return;
                }
            }
            // 判断用户角色是否为url所需角色
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            // 遍历当前的权限集合
            for (GrantedAuthority authority : authorities) {
                // 如果权限集合中的角色与当前url所需要的角色一致
                if (authority.getAuthority().equals(needRole)) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("权限不足，请联系管理员！");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return false;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
