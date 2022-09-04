package com.zrkizzy.template.filters;

import com.zrkizzy.template.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Jwt登录授权过滤器
 *
 * @author zhangrongkang
 * @date 2022/8/7
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    /**
     * Jwt存储的请求头
     */
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    /**
     * Jwt负载中的开头
     */
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Resource
    private JwtTokenUtil jwtTokenUtil;
    @Resource
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. 从当前请求中获取到授权数据
        String authHeader = request.getHeader(tokenHeader);
        // 2. 判断获取到的请求头中是否存在token
        if (authHeader != null && authHeader.startsWith(tokenHead)) {
            // 如果存在token则将token令牌截取出来
            String authToken = authHeader.substring(tokenHead.length());
            // 从token中获取到当前登录用户的用户名
            String userName = jwtTokenUtil.getUserNameFromToken(authToken);
            // 如果token存在用户名，但是获取不到用户对象说明用户未登录
            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // 执行登录操作
                UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
                // 同时判断token是否有效，如果有效则重新设置用户对象
                if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        // 放行
        filterChain.doFilter(request, response);
    }
}
