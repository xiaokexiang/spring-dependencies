package io.spring.springsecurity.config.dynamic;

import io.spring.common.response.ResponseBody;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xiaokexiang
 * @since 2021/3/19
 * 处理未登录情况下所有访问的接口（放行除外）
 */
public class AdminAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.getWriter().append(ResponseBody.error("您需要登录！").toString());
        response.flushBuffer();
    }
}
