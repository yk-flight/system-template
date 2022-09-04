package com.zrkizzy.template.service.impl;

import com.zrkizzy.template.entity.User;
import com.zrkizzy.template.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zhangrongkang
 * @date 2022/8/6
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据当前用户名到数据库中进行查询
        User user = userService.getUserByUserName(username);
        if (user != null) {
            // 设置当前用户的角色
            user.setRoles(userService.getRoles(user.getId()));
            return user;
        }
        throw new UsernameNotFoundException("用户名或密码不正确");
    }
}
