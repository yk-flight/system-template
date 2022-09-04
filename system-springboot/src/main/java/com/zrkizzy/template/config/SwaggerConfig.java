package com.zrkizzy.template.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger接口文档配置类
 *
 * @author zhangrongkang
 * @date 2022/8/4
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                // 接口文档的参数信息
                .apiInfo(apiInfo())
                .select()
                // 包扫描的位置
                .apis(RequestHandlerSelectors.basePackage("com.zrkizzy.template.controller"))
                .paths(PathSelectors.any())
                .build()
                .securityContexts(securityContexts())
                .securitySchemes(securitySchemes());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 定义标题
                .title("后台管理系统接口文档")
                // 接口文档描述
                .description("基于SpringBoot + Vue2开发的前后端分离的后台管理系统模板，采用SpringSecurity + JWT进行权限管理。项目前端采用element-ui、Vuex、axios等进行样式渲染以及动画实现，页面交互友好。同时项目后端使用RBAC动态权限体系，支持动态权限修改。项目代码相对简易、无过度封装和任何业务入侵，便于进行二次开发。")
                // 接口文档作者信息
                .contact(new Contact("世纪末的架构师", "http:localhost:8090/doc.html", "1072876976@qq.com"))
                // 版本号
                .version("1.0")
                .build();
    }

    private List<SecurityScheme> securitySchemes() {
        // 设置请求头信息
        List<SecurityScheme> result = new ArrayList<>();
        ApiKey apiKey = new ApiKey("Authorization", "Authorization", "Header");
        result.add(apiKey);
        return result;
    }

    private List<SecurityContext> securityContexts() {
        // 设置需要登录认证的路径
        List<SecurityContext> result = new ArrayList<>();
        result.add(getContextByPath("/hello/.*"));
        return result;
    }

    private SecurityContext getContextByPath(String pathRegex) {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex(pathRegex))
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        List<SecurityReference> result = new ArrayList<>();
        // 设置授权范围，全局
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        // 需要使用数组来将默认的授权范围进行存储
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        // 第二个参数就是数组
        result.add(new SecurityReference("Authorization", authorizationScopes));
        return result;
    }
}
