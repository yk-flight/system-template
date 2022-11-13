package com.zrkizzy.template.handler;

import com.zrkizzy.template.entity.Menu;
import com.zrkizzy.template.entity.Role;
import com.zrkizzy.template.service.IMenuService;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * 权限控制过滤器：根据请求url分析请求所需的角色
 *
 * @author zhangrongkang
 * @date 2022/8/7
 */
@Component
public class FilterInvocationSecurityMetadataSourceImpl implements FilterInvocationSecurityMetadataSource {
    @Resource
    private IMenuService IMenuService;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        // 获取请求的url
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        // 获取到所有的菜单
        List<Menu> menus = IMenuService.getMenuWithRole();
        // 匹配请求的url与菜单角色中的url是否匹配
        for (Menu menu : menus) {
            if (antPathMatcher.match(menu.getUrl(), requestUrl)) {
                String[] str = menu.getRoles().stream().map(Role::getRoleName).toArray(String[]::new);
                return SecurityConfig.createList(str);
            }
        }
        // 没有匹配到的url默认登录即可访问
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
