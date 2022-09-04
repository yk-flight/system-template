package com.zrkizzy.template.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 响应状态枚举类
 *
 * @author zhangrongkang
 * @date 2022/9/3
 */
@Getter
@AllArgsConstructor
public enum StatusEnum {

    /**
     * 响应成功
     */
    SUCCESS(200, true, "响应成功"),
    /**
     * 未找到路径
     */
    PATH_ERROR(404, false, "未找到路径"),
    /**
     * 请求方式错误
     */
    METHOD_ERROR(405, false, "请求方式错误"),
    /**
     * 权限不足
     */
    PERMISSION_ERROR(403, false, "权限不足"),
    /**
     * 用户未登录
     */
    LOGIN_ERROR(401, false, "用户未登录");

    /**
     * 状态码
     */
    private final Integer code;
    /**
     * 执行结果
     */
    private final Boolean result;
    /**
     * 请求描述
     */
    private final String description;

    /**
     * 获取状态码枚举
     *
     * @param code 状态码
     * @return 状态码枚举
     */
    public static StatusEnum getStatusEnum(Integer code) {
        for (StatusEnum value : StatusEnum.values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return null;
    }

    /**
     * 获取状态码描述
     *
     * @param code 状态码
     * @return 状态码描述
     */
    public static String getStatusDescription(Integer code) {
        for (StatusEnum value : StatusEnum.values()) {
            if (value.getCode().equals(code)) {
                return value.getDescription();
            }
        }
        return null;
    }

    /**
     * 根据状态码获取响应结果
     *
     * @param code 状态码
     * @return 状态码响应结果
     */
    public static Boolean getStatusResult(Integer code) {
        for (StatusEnum value : StatusEnum.values()) {
            if (value.getCode().equals(code)) {
                return value.getResult();
            }
        }
        return false;
    }
}
