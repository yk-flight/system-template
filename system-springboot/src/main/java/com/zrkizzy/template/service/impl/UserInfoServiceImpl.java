package com.zrkizzy.template.service.impl;

import com.zrkizzy.template.annotation.LogAnnotation;
import com.zrkizzy.template.entity.User;
import com.zrkizzy.template.entity.UserInfo;
import com.zrkizzy.template.mapper.UserInfoMapper;
import com.zrkizzy.template.mapper.UserMapper;
import com.zrkizzy.template.service.UserInfoService;
import com.zrkizzy.template.utils.BeanCopyUtil;
import com.zrkizzy.template.utils.UserUtil;
import com.zrkizzy.template.vo.Result;
import com.zrkizzy.template.vo.UserInfoVO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author zhangrongkang
 * @date 2022/8/15
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 获取当前登录用户的个人信息
     *
     * @return 当前登录用户的个人信息
     */
    @Override
    public UserInfo getCurrentUserInfo() {
        // 获取用户ID
        Integer userId = UserUtil.getCurrentUser().getId();
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        // 从Redis中获取个人信息数据
        UserInfo userInfo = (UserInfo) valueOperations.get("userInfo_" + userId);
        if (userInfo == null) {
            // 如果Redis中不存在个人信息数据则到数据库中查询，查询后存储到Redis中
            userInfo = userInfoMapper.selectById(userId);
            valueOperations.set("userInfo_" + userId, userInfo);
        }
        return userInfo;
    }

    /**
     * 更新登录用户的个人信息
     *
     * @param userInfoVO 用户信息对象
     * @return 前端返回对象
     */
    @Override
    @LogAnnotation(module = "用户信息模块", description = "更新用户个人信息")
    @Transactional(rollbackFor = RuntimeException.class)
    public Result updateUserInfo(UserInfoVO userInfoVO) {
        // 获取当前登录用户ID
        Integer userId = userInfoVO.getId();
        // 查询当前用户的昵称和账号
        User user = userMapper.selectById(userId);
        // 判断昵称是否需要修改
        if (!user.getNickName().equals(userInfoVO.getNickName())) {
            // 如果昵称需要更新则将用户昵称进行设置
            user.setNickName(userInfoVO.getNickName());
        }
        // 判断用户账号是否需要修改
        if (!StringUtils.isEmpty(userInfoVO.getUsername()) && !user.getUsername().equals(userInfoVO.getUsername())) {
            // 如果需要更新账号
            user.setUsername(userInfoVO.getUsername());
        }
        // 更新用户上一次更新时间
        user.setUpdateTime(new Date());
        // 更新用户信息
        userMapper.updateById(user);
        UserInfo userInfo = BeanCopyUtil.copy(userInfoVO, UserInfo.class);
        // 设置当前用户对象的ID
        userInfo.setId(userId);
        // 获取受到影响到行数
        int count = userInfoMapper.updateById(userInfo);
        // 更新Redis中的用户信息对象
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("userInfo_" + userId, userInfo);

        if (count > 0) {
            return Result.success("信息更新成功");
        }
        return Result.error("信息更新失败");
    }

    /**
     * 通过用户ID获取指定的用户信息
     *
     * @param userId 用户ID
     * @return 指定的用户信息
     */
    @Override
    public UserInfo getUserInfoById(Integer userId) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        // 从Redis中获取个人信息数据
        UserInfo userInfo = (UserInfo) valueOperations.get("userInfo_" + userId);
        if (userInfo == null) {
            // 如果Redis中不存在个人信息数据则到数据库中查询，查询后存储到Redis中
            userInfo = userInfoMapper.selectById(userId);
            valueOperations.set("userInfo_" + userId, userInfo);
        }
        return userInfo;
    }

    /**
     * 修改用户是否启用状态
     *
     * @param userId 用户ID
     * @return 前端响应对象
     */
    @Override
    @LogAnnotation(module = "用户信息模块", description = "修改用户是否启用")
    @Transactional(rollbackFor = RuntimeException.class)
    public Result changeUserEnabled(Integer userId) {
        User user = userMapper.selectById(userId);
        user.setEnabled(!user.isEnabled());
        int count = userMapper.updateById(user);
        if (count > 0) {
            return Result.success("用户状态更新成功");
        }
        return Result.error("用户状态更新失败");
    }
}
