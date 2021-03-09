package io.spring.springsecurity.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author xiaokexiang
 * @since 2021/3/8
 */
public class HttpParameterWrapper extends HttpServletRequestWrapper {
    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public HttpParameterWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getParameter(String name) {
        // 因为preLoginFilter的下个过滤器UsernamePasswordAuthenticationFilter会调用getParameter()方法
        // 将其包装成实际调用getAttribute()
        return (String) super.getAttribute(name);
    }
}
