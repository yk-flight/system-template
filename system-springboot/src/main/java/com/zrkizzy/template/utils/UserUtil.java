package com.zrkizzy.template.utils;

import com.zrkizzy.template.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户操作工具类
 *
 * @author zhangrongkang
 * @date 2022/8/13
 */
public class UserUtil {

    /**
     * 获取当前登录的用户
     *
     * @return 当前登录的用户对象
     */
    public static User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * 通过用户传来的权限字符进行分割出权限
     *
     * @param permission 权限字符
     * @return 分割后到权限数组
     */
    public static List<Integer> getPermissionByString(String permission) {
        if (StringUtils.isEmpty(permission)) {
            return null;
        }
        // 去除permission两边的[]
        permission = permission.substring(1, permission.length() - 1);
        // 根据 , 进行分割
        String[] split = permission.split(",");
        List<Integer> result = new ArrayList<>();
        for (String s : split) {
            result.add(Integer.parseInt(s));
        }
        return result;
    }

    public static String setPermissionToString(List<Integer> permission) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (int i = 0; i < permission.size(); i++) {
            builder.append(permission.get(i));
            if (i != permission.size() - 1) {
                builder.append(",");
            }
        }
        builder.append("]");
        return builder.toString();
    }
}
