package com.zrkizzy.template.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 资源访问配置类
 *
 * @author zhangrongkang
 * @date 2022/9/2
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    /**
     * 文件上传路径
     */
    @Value("${file.path}")
    public String FILE_PATH;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 所有带 "/images" 的请求都认为是静态资源请求
        registry.addResourceHandler("/images/**").addResourceLocations("file:" + FILE_PATH);
    }
}
