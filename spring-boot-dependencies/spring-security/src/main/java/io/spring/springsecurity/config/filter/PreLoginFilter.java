package io.spring.springsecurity.config.filter;

import com.alibaba.fastjson.JSONObject;
import io.spring.springsecurity.config.HttpParameterWrapper;
import io.spring.springsecurity.config.RequestUtil;
import io.spring.springsecurity.controller.login.LoginPostProcessor;
import io.spring.springsecurity.controller.login.LoginTypeEnum;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.annotation.Order;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaokexiang
 * @since 2021/3/8
 */
@Order(SecurityProperties.BASIC_AUTH_ORDER)
public class PreLoginFilter extends GenericFilterBean {

    private RequestMatcher requiresAuthenticationRequestMatcher;
    private Map<LoginTypeEnum, LoginPostProcessor> processors = new HashMap<>();

    // 对某个路径进行拦截进行处理
    public PreLoginFilter(String loginProcessingUrl, Collection<LoginPostProcessor> loginPostProcessors) {
        Assert.notNull(loginProcessingUrl, "loginProcessingUrl must not be null");
        requiresAuthenticationRequestMatcher = new AntPathRequestMatcher(loginProcessingUrl, "POST");
        LoginPostProcessor loginPostProcessor = defaultLoginProcessor();
        LoginPostProcessor jsonLoginProcessor = jsonLoginProcessor();
        processors.put(loginPostProcessor.getLoginTypeEnum(), loginPostProcessor);
        processors.put(jsonLoginProcessor.getLoginTypeEnum(), jsonLoginProcessor);
        if (!CollectionUtils.isEmpty(loginPostProcessors)) {
            loginPostProcessors.forEach(l -> processors.put(l.getLoginTypeEnum(), l));
        }
    }


    private LoginTypeEnum getType(ServletRequest request) {
        int loginType;
        try {
            loginType = Integer.parseInt(request.getParameter("loginType"));
        } catch (Exception e) {
            return LoginTypeEnum.FROM;
        }
        // check loginType
        LoginTypeEnum[] values = LoginTypeEnum.values();
        if (loginType >= values.length || loginType < 0) {
            return LoginTypeEnum.FROM;
        }
        return values[loginType];
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        // 对request进行包装
        HttpParameterWrapper requestWrapper = new HttpParameterWrapper(request);
        if (requiresAuthenticationRequestMatcher.matches(request)) {
            LoginTypeEnum type = getType(request);
            LoginPostProcessor processor = processors.getOrDefault(type, defaultLoginProcessor());
            String username = processor.obtainUsername(request);
            String password = processor.obtainPassword(request);
            requestWrapper.setAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, username);
            requestWrapper.setAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY, password);
        }
        // 将用户名和密码传递到下个filter（UsernamePasswordAuthenticationFilter）进行处理
        chain.doFilter(requestWrapper, servletResponse);
    }

    private DefaultLoginProcessor defaultLoginProcessor() {
        return new DefaultLoginProcessor();
    }

    private JsonLoginProcessor jsonLoginProcessor() {
        return new JsonLoginProcessor();
    }

    private static class DefaultLoginProcessor implements LoginPostProcessor {

        /**
         * 默认form表达形式，用户名密码表单传入
         */
        @Override
        public LoginTypeEnum getLoginTypeEnum() {
            return LoginTypeEnum.FROM;
        }

        @Override
        public String obtainUsername(ServletRequest request) {
            return request.getParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY);
        }

        @Override
        public String obtainPassword(ServletRequest request) {
            return request.getParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY);
        }
    }

    private static class JsonLoginProcessor implements LoginPostProcessor {

        private static final ThreadLocal<String> passwordThreadLocal = new ThreadLocal<>();

        @Override
        public LoginTypeEnum getLoginTypeEnum() {
            return LoginTypeEnum.JSON;
        }

        @Override
        public String obtainUsername(ServletRequest request) {
            JSONObject jsonObject = JSONObject.parseObject(RequestUtil.obtainBody(request));
            passwordThreadLocal.set(jsonObject.getString(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY));
            return jsonObject.getString(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY);
        }

        @Override
        public String obtainPassword(ServletRequest request) {
            String password;
            try {
                password = passwordThreadLocal.get();
            } finally {
                passwordThreadLocal.remove();
            }
            return password;
        }
    }
}
