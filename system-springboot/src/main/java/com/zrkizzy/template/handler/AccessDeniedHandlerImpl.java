package com.zrkizzy.template.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zrkizzy.template.vo.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 当访问接口没有权限时，自定义返回结果
 *
 * @author zhangrongkang
 * @date 2022/8/7
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        // 获取输出流
        PrintWriter out = response.getWriter();
        Result bean = Result.error("权限不足，请联系管理员");
        bean.setCode(403);
        out.write(new ObjectMapper().writeValueAsString(bean));
        out.flush();
        out.close();
    }
}
