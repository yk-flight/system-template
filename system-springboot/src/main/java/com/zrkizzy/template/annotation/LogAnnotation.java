package com.zrkizzy.template.annotation;

import java.lang.annotation.*;

/**
 * AOP切面自定义注解
 *
 * @author zhangrongkang
 * @date 2022/9/3
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {
    /**
     * 操作模块
     */
    String module() default "";
    /**
     * 操作描述
     */
    String description() default "";
}
