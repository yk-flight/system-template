package com.zrkizzy.template.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zrkizzy.template.annotation.LogAnnotation;
import com.zrkizzy.template.entity.Role;
import com.zrkizzy.template.entity.User;
import com.zrkizzy.template.entity.UserInfo;
import com.zrkizzy.template.entity.UserRole;
import com.zrkizzy.template.mapper.*;
import com.zrkizzy.template.service.IUserService;
import com.zrkizzy.template.utils.*;
import com.zrkizzy.template.vo.PageVO;
import com.zrkizzy.template.vo.Result;
import com.zrkizzy.template.vo.AvatarVO;
import com.zrkizzy.template.vo.PasswordVO;
import com.zrkizzy.template.vo.UserInfoVO;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.zrkizzy.template.constant.CommonConst.DOMAIN;
import static com.zrkizzy.template.constant.CommonConst.LOCAL_HOST;

/**
 * @author zhangrongkang
 * @date 2022/8/7
 */
@Service
public class IUserServiceImpl implements IUserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private UserRoleMapper userRoleMapper;
    @Resource
    private MenuMapper menuMapper;

    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Value("${file.path}")
    private String path;

    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private JwtTokenUtil jwtTokenUtil;

    /**
     * 通过用户名获取用户对象
     *
     * @param username 用户名
     * @return 用户对象
     */
    @Override
    public User getUserByUserName(String username) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
    }

    /**
     * 用户登录后返回token
     *
     * @param username 用户名
     * @param password 密码
     * @param code     验证码
     * @param request  请求
     * @return 携带token的返回对象
     */
    @Override
    public Result login(String username, String password, String code, HttpServletRequest request) {
        // 1. -------------------- 判断验证码 --------------------
        // 从Session中获取验证码
        String kaptcha = (String) request.getSession().getAttribute("kaptcha");
        // 判断验证码是否正确
        if (StringUtils.isEmpty(kaptcha) || !kaptcha.equals(code)) {
            return Result.error("验证码输入错误，请重新输入");
        }
        // 2. -------------------- 判断用户名和密码 --------------------
        // 获取到UserDetails
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        // 如果userDetails为空或密码匹配不一致
        if (null == userDetails || !passwordEncoder.matches(password, userDetails.getPassword())) {
            return Result.error("用户名或密码错误");
        }

        // 3. -------------------- 判断账号是否被禁用 --------------------
        // 查看用户是否被禁用
        if (!userDetails.isEnabled()) {
            return Result.error("当前账号已禁用，请联系管理员");
        }

        // 4. -------------------- 更新用户登录信息 --------------------
        updateLastLoginInfo(username, request);

        // 更新security登录用户对象，参数1：userDetails，参数2：密码，参数3：用户权限列表
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        // security全局更新用户权限
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // 通过userDetails生成token
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String, String> tokenMap = new HashMap<>();
        // 令牌
        tokenMap.put("token", token);
        // 头部信息
        tokenMap.put("tokenHead", tokenHead);
        // 登录成功后将token返回给前端
        return Result.success("登录成功", tokenMap);
    }

    /**
     * 通过用户ID获取对应的用户权限
     *
     * @param userId 用户ID
     * @return 当前用户的权限集合
     */
    @Override
    public List<Role> getRoles(Integer userId) {
        return roleMapper.getRoles(userId);
    }

    /**
     * 更新指定用户上一次登录时间
     *
     * @param username 用户名
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateLastLoginInfo(String username, HttpServletRequest request) {
        // 通过用户名获取到指定用户对象
        User user = getUserByUserName(username);
        // 重新设置当前用户上一次登录的时间
        user.setLastLoginTime(new Date());
        // 获取到用户登录IP
        String ipAddress = IpUtil.getCurIpAddress(request);
        // 获取到用户登录的IP属地
        String ipSource = IpUtil.getIpSource(ipAddress);
        // 判断是否获取到了真实IP地址，如果没有获取到真实IP地址则设置为本机
        if (ipAddress.equals(LOCAL_HOST)) {
            ipSource = "本地登录";
        }
        // 设置用户的IP信息
        user.setIpAddress(ipAddress);
        user.setIpSource(ipSource);
        // 更新用户信息
        userMapper.updateById(user);
    }

    /**
     * 获取当前登录的用户信息
     *
     * @return 当前登录的用户对象
     */
    @Override
    public User getUserByUserId() {
        // 获取当前登录用户的ID
        Integer userId = UserUtil.getCurrentUser().getId();
        // 根据用户ID查询到用户对象
        User user = userMapper.selectById(userId);
        // 设置用户的角色
        List<Role> roles = roleMapper.getRoles(userId);
        user.setRoles(roles);
        // 查询到当前角色的ID
        String rolePermission = roleMapper.selectByUserId(userId);
        // 获取到当前用户可以访问的路径集合
        List<Integer> permission = UserUtil.getPermissionByString(rolePermission);
        user.setPermission(menuMapper.selectPermission(permission));
        return user;
    }

    /**
     * 获取用户登录设备
     *
     * @param request 请求信息
     * @return 用户登录设备
     */
    @Override
    public String getUserAgent(HttpServletRequest request) {
        UserAgent userAgent = IpUtil.getUserAgent(request);
        // 拼接操作系统信息
        return userAgent.getOperatingSystem().getName() + " " +
                // 拼接浏览器信息
                userAgent.getBrowser();
    }

    /**
     * 用户更新密码
     *
     * @param passwordVO 用户密码参数对象
     * @return 返回结果对象
     */
    @Override
    @LogAnnotation(module = "用户模块", description = "用户更新密码")
    @Transactional(rollbackFor = RuntimeException.class)
    public Result updatePassword(PasswordVO passwordVO) {
        // 判断新旧密码是否一致
        if (passwordEncoder.matches(passwordVO.getNewPassword(), passwordVO.getOldPassword())) {
            return Result.error("新密码不能与旧密码相同");
        }
        // 获取当前登录用户的ID
        Integer userId = UserUtil.getCurrentUser().getId();
        // 通过用户ID查询到对应的用户对象
        User user = userMapper.selectById(userId);

        String password = passwordEncoder.encode(passwordVO.getNewPassword());
        // 更新上一次用户更新时间
        user.setUpdateTime(new Date());
        // 更新用户密码
        user.setPassword(password);

        int count = userMapper.updateById(user);
        if (count > 0) {
            return Result.success("更新密码成功");
        }
        return Result.error("更新密码失败");
    }

    /**
     * 获取用户列表
     *
     * @param curPage  当前页面
     * @param size     页面大小
     * @param username 用户名
     * @return 分页对象
     */
    @Override
    public PageVO getUserList(Integer curPage, Integer size, String username) {
        // 开启分页
        Page<User> page = new Page<>(curPage, size);
        // 定义查询条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username", username);
        Page<User> userPage = userMapper.selectPage(page, queryWrapper);
        return new PageVO(userPage.getTotal(), userPage.getRecords());
    }

    /**
     * 新增用户
     *
     * @param userInfoVO 用户数据传递对象
     * @return 前端响应对象
     */
    @Override
    @LogAnnotation(module = "用户模块", description = "新增用户")
    @Transactional(rollbackFor = RuntimeException.class)
    public Result addUser(UserInfoVO userInfoVO) {
        User user = BeanCopyUtil.copy(userInfoVO, User.class);
        user.setEnabled(true);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setPassword(passwordEncoder.encode("123456"));
        // 新增用户信息
        int insertUser = userMapper.insert(user);
        // 根据用户信息获取到用户ID
        Integer userId = userMapper.selectOne(new QueryWrapper<User>().eq("username", userInfoVO.getUsername())).getId();
        // 根据用户ID新增用户信息
        UserInfo userInfo = BeanCopyUtil.copy(userInfoVO, UserInfo.class);
        userInfo.setId(userId);
        int insertUserInfo = userInfoMapper.insert(userInfo);
        // 为新增用户赋值角色
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(userInfoVO.getRoleId());
        int insertRole = userRoleMapper.insert(userRole);
        if (insertUser > 0 && insertUserInfo > 0 && insertRole > 0) {
            return Result.success("添加用户成功");
        }
        return Result.error("添加用户失败");
    }

    /**
     * 根据用户ID删除用户
     *
     * @param userId 用户ID
     * @return 前端响应对象
     */
    @Override
    @LogAnnotation(module = "用户模块", description = "删除用户")
    @Transactional(rollbackFor = RuntimeException.class)
    public Result deleteUserById(Integer userId) {
        // 1. 删除user表中的数据
        int deleteUser = userMapper.deleteById(userId);
        // 2. 删除user_info表格中的数据
        int deleteUserInfo = userInfoMapper.deleteById(userId);
        // 3. 删除user_role表中的数据
        int deleteUserRole = userRoleMapper.delete(new QueryWrapper<UserRole>().eq("user_id", userId));
        if (deleteUser > 0 && deleteUserInfo > 0 && deleteUserRole > 0) {
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }

    /**
     * 根据用户ID修改密码
     *
     * @param userId   用户ID
     * @param password 新密码
     * @return 前端响应对象
     */
    @Override
    @LogAnnotation(module = "用户模块", description = "管理员更新指定用户密码")
    @Transactional(rollbackFor = RuntimeException.class)
    public Result updatePasswordById(Integer userId, String password) {
        User user = userMapper.selectById(userId);
        // 更新用户密码
        user.setPassword(passwordEncoder.encode(password));
        int count = userMapper.updateById(user);
        if (count > 0) {
            return Result.success("重置成功");
        }
        return Result.error("重置失败");
    }

    /**
     * 上传用户头像
     *
     * @param file 上传文件
     * @return 前端响应对象
     */
    @Override
    public Result uploadAvatar(MultipartFile file) throws IOException {
        // 获取文件类型
        int index = file.getOriginalFilename().lastIndexOf(".") + 1;
        String suffix = file.getOriginalFilename().substring(index);
        String fileName = UuidUtil.getShortUuid() + "." + suffix;
        // 拼接文件的全路径
        String fullPath = path +
                // 拼接 "/"
                File.separator +
                // 拼接文件名
                fileName;

        // 如果不存在文件保存的位置
        if (!Files.exists(Paths.get(path))) {
            // 创建文件夹
            Files.createDirectory(Paths.get(path));
        }
        // 保存上传的文件
        file.transferTo(new File(fullPath));
        // 定义返回的路径
        String avatarUrl = DOMAIN + fileName;
        return Result.success("头像上传成功", avatarUrl);
    }

    /**
     * 更新用户头像路径
     *
     * @param avatarVO 用户头像参数接收对象
     */
    @Override
    @LogAnnotation(module = "用户模块", description = "更新用户头像路径")
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateAvatarById(AvatarVO avatarVO) {
        // 获取到当前登录用户的ID
        Integer userId = UserUtil.getCurrentUser().getId();
        // 获取到当前用户
        User user = userMapper.selectById(userId);
        user.setAvatar(avatarVO.getAvatar());
        user.setUpdateTime(new Date());
        // 更新用户头像和上一次更新时间
        userMapper.updateById(user);
    }


}
