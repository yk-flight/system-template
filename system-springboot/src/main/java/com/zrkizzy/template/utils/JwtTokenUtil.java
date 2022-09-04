package com.zrkizzy.template.utils;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Jwt处理Token工具类
 *
 * @author zhangrongkang
 * @date 2022/8/7
 */
@Component
public class JwtTokenUtil {
    /**
     * 用户名的key
     */
    private static final String CLAIM_KEY_USERNAME = "sub";
    /**
     * JWT的创建时间
     */
    private static final String CLAIM_KEY_CREATED = "created";
    /**
     * 秘钥
     */
    @Value("${jwt.secret}")
    private String secret;
    /**
     * token失效时间
     */
    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 根据用户信息生成token
     *
     * @param userDetails 用户信息
     * @return 生成的token
     */
    public String generateToken(UserDetails userDetails) {
        // 定义token中存储数据的载荷
        Map<String, Object> claims = new HashMap<>();
        // 1. 用户名
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        // 2. 签发时间
        claims.put(CLAIM_KEY_CREATED, new Date());
        // 签发token
        return generateToken(claims);
    }

    /**
     * 根据载荷生成token
     *
     * @param claims 载荷
     * @return 生成的token
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                // 1. 设置载荷
                .setClaims(claims)
                // 2. 设置失效时间
                .setExpiration(generateExpirationDate())
                // 3. 设置签名
                .signWith(SignatureAlgorithm.HS512, secret)
                // 签发token
                .compact();
    }

    /**
     * 生成token失效时间
     *
     * @return token的失效时间
     */
    private Date generateExpirationDate() {
        // token失效时间：当前系统时间 + 自己定义的时间
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
     * 从token中获取用户名
     *
     * @param token token
     * @return 当前token中存储的用户名
     */
    public String getUserNameFromToken(String token) {
        String userName;
        try {
            // 从token中获取到载荷
            Claims claims = getClaimsFromToken(token);
            // 通过载荷获取到用户名
            userName = claims.getSubject();
        } catch (Exception e) {
            // 如果出现异常则将 userName 设置为空
            userName = null;
        }
        return userName;
    }

    /**
     * 从token中获取载荷
     *
     * @param token token
     * @return token中的载荷
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    // 解密的秘钥
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }

    /**
     * 判断token是否可以被刷新
     *
     * @param token token
     * @return token是否可以被刷新
     */
    public boolean canRefresh(String token) {
        return !isTokenExpired(token);
    }

    /**
     * 判断token是否失效
     *
     * @param token token
     * @return 当前token是否失效
     */
    private boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFromToken(token);
        // 如果失效时间是在当前时间之前肯定是失效的
        return expiredDate.before(new Date());
    }

    /**
     * 获取token中的失效时间
     *
     * @param token token
     * @return 当前token中的失效时间
     */
    private Date getExpiredDateFromToken(String token) {
        // 从当前token中获取载荷
        Claims claims = getClaimsFromToken(token);
        // 从载荷中返回失效时间
        return claims.getExpiration();
    }

    /**
     * 刷新token
     *
     * @param token 用户携带的 token
     * @return 刷新后的 token
     */
    public String refreshToken(String token) {
        // 从当前token中获取载荷
        Claims claims = getClaimsFromToken(token);
        // 更新载荷中的失效时间改成当前时间
        claims.put(CLAIM_KEY_CREATED, new Date());
        // 重新生成token
        return generateToken(claims);
    }

    /**
     * 判断token是否有效
     *
     * @param token 用户携带的 token
     * @param userDetails 载荷
     * @return token 是否有效
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        // 通过token是否已经过期、荷载中的用户属性与userDetails中的用户属性是否一致
        String userName = getUserNameFromToken(token);
        return userName.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
}
