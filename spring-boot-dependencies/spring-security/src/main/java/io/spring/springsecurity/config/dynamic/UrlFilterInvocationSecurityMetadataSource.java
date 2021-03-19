package io.spring.springsecurity.config.dynamic;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author xiaokexiang
 * @since 2021/3/19
 */
@Component
public class UrlFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        // FilterInvocation是与http对象相关联
        FilterInvocation f = (FilterInvocation) object;
        String url = f.getRequestUrl();
        if (url.contains("/login") || url.contains("/logout")) {
            return null;
        }
        // 此处处理动态权限
        return SecurityConfig.createList("ADMIN", "QUERY");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }
}
