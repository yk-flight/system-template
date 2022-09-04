package com.zrkizzy.template.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zrkizzy.template.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zhangrongkang
 * @date 2022/8/15
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {
}
