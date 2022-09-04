package com.zrkizzy.template.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 公共返回对象
 *
 * @author zhangrongkang
 * @date 2022/8/7
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 提示信息
     */
    private String message;
    /**
     * 返回对象
     */
    private Object data;

    /**
     * 成功返回结果
     *
     * @param message 提示信息
     * @return 返回成功
     */
    public static Result success(String message) {
        return new Result(200, message, null);
    }

    /**
     * 成功返回结果
     *
     * @param message 提示信息
     * @param data 返回数据
     * @return 返回成功
     */
    public static Result success(String message, Object data) {
        return new Result(200, message, data);
    }

    /**
     * 失败返回结果
     *
     * @param message 提示信息
     * @return 返回失败
     */
    public static Result error(String message) {
        return new Result(500, message, null);
    }

    /**
     * 失败返回结果
     *
     * @param message 提示信息
     * @param data 返回数据
     * @return 返回失败
     */
    public static Result error(String message, Object data) {
        return new Result(500, message, data);
    }
}
