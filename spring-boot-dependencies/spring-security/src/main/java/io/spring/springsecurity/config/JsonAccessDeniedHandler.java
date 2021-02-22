package io.spring.springsecurity.config;

import com.alibaba.fastjson.JSONObject;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xiaokexiang
 * @since 2021/2/22
 * 处理无权限返回json信息
 */
public class JsonAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        JSONObject result = new JSONObject();
        result.put("code", 400);
        result.put("message", "No Authorized 您无此权限");
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().append(result.toJSONString());
    }
}
