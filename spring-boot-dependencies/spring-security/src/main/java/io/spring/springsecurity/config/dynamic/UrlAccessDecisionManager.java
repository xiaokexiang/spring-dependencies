package io.spring.springsecurity.config.dynamic;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author xiaokexiang
 * @since 2021/3/19
 */
public class UrlAccessDecisionManager implements AccessDecisionManager {

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        for (ConfigAttribute configAttribute : configAttributes) {
            // 未登录ROLE_ANONYMOUS角色
            if (authentication instanceof AnonymousAuthenticationToken) {
                throw new BadCredentialsException("未登录!");
            }
            // ① 当前url请求需要的权限
            String role = configAttribute.getAttribute();
            // ② 当前用户所具有的角色
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                // 只要包含其中一个角色即可访问
                if (authority.getAuthority().equals(role)) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("请联系管理员分配权限！");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return false;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }
}
