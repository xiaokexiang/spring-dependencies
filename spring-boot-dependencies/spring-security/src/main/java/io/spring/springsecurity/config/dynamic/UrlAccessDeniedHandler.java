package io.spring.springsecurity.config.dynamic;

import io.spring.common.response.ResponseBody;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xiaokexiang
 * @since 2021/3/19
 * 处理登录后没有权限访问的路径
 */
public class UrlAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.getWriter().append(ResponseBody.error("您无此权限！").toString());
        response.flushBuffer();
    }
}
