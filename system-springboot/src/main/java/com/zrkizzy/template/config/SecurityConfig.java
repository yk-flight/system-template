package com.zrkizzy.template.config;

import com.zrkizzy.template.filters.JwtAuthenticationTokenFilter;
import com.zrkizzy.template.handler.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * Spring Security配置类
 *
 * @author zhangrongkang
 * @date 2022/8/6
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 当前用户未登录或token失效时自定义返回结果
     */
    @Resource
    private AuthenticationEntryPointImpl authenticationEntryPoint;
    /**
     * 当前接口没有权限时的自定义返回结果
     */
    @Resource
    private AccessDeniedHandlerImpl accessDeniedHandler;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AccessDecisionManager accessDecisionManager() {
        return new AccessDecisionManagerImpl();
    }

    @Bean
    public FilterInvocationSecurityMetadataSource securityMetadataSource() {
        return new FilterInvocationSecurityMetadataSourceImpl();
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                // 放行登录和退出
                "/login",
                "/logout",
                // 放行静态资源文件
                "/css/**",
                "/js/**",
                // 放行网站首页
                "/index.html",
                // 放行网站图标
                "favicon.ico",
                // 放行swagger相关文件
                "/doc.html",
                "/webjars/**",
                "/swagger-resources/**",
                "/v2/api-docs/**",
                // 放行验证码
                "/kaptcha",
                "/images/**"
        );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 使用Jwt，不需要使用csrf
        http.csrf().disable()
                // 基于token，不需要session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 动态权限配置
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        // 判断用户角色以及Url中所需要的角色
                        object.setAccessDecisionManager(accessDecisionManager());
                        // 根据请求中的Url分析所需要的角色
                        object.setSecurityMetadataSource(securityMetadataSource());
                        return object;
                    }
                })
                // 其他所有请求都要认证
                .anyRequest().authenticated()
                .and()
                // 禁用缓存
                .headers()
                .cacheControl();

        // 添加Jwt登录授权过滤器
        http.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        // 添加自定义未授权和未登录结果返回
        http.exceptionHandling()
                // 没有权限时的返回结果
                .accessDeniedHandler(accessDeniedHandler)
                // 未登录时的返回结果
                .authenticationEntryPoint(authenticationEntryPoint);
    }
}
