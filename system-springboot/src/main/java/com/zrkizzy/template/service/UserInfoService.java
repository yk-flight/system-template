package com.zrkizzy.template.service;

import com.zrkizzy.template.dto.UserInfoDTO;
import com.zrkizzy.template.entity.UserInfo;
import com.zrkizzy.template.vo.Result;

/**
 * @author zhangrongkang
 * @date 2022/8/15
 */
public interface UserInfoService {
    /**
     * 获取当前登录用户的个人信息
     *
     * @return 当前登录用户的个人信息
     */
    UserInfo getCurrentUserInfo();

    /**
     * 更新登录用户的个人信息
     *
     * @param userInfoDTO 用户信息传输对象
     * @return 前端返回对象
     */
    Result updateUserInfo(UserInfoDTO userInfoDTO);

    /**
     * 通过用户ID获取指定的用户信息
     *
     * @param userId 用户ID
     * @return 指定的用户信息
     */
    UserInfo getUserInfoById(Integer userId);

    /**
     * 修改用户是否启用状态
     *
     * @param userId 用户ID
     * @return 前端响应对象
     */
    Result changeUserEnabled(Integer userId);
}
