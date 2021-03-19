package io.spring.springsecurity.config;

import io.spring.springsecurity.config.dynamic.AdminAuthenticationEntryPoint;
import io.spring.springsecurity.config.dynamic.UrlAccessDecisionManager;
import io.spring.springsecurity.config.dynamic.UrlAccessDeniedHandler;
import io.spring.springsecurity.config.dynamic.UrlFilterInvocationSecurityMetadataSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import javax.annotation.Resource;

/**
 * @author xiaokexiang
 * @since 2021/3/19
 * 权限的调试
 */
@Configuration
public class PermissionSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserManager userManager;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userManager).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.antMatcher("/**").authorizeRequests();

        http.csrf().disable().cors();

        // 未登录认证异常
        http.exceptionHandling().authenticationEntryPoint(new AdminAuthenticationEntryPoint());
        // 登录过后访问无权限的接口时自定义403响应内容
        http.exceptionHandling().accessDeniedHandler(new UrlAccessDeniedHandler());
        registry.withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
            @Override
            public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                object.setSecurityMetadataSource(new UrlFilterInvocationSecurityMetadataSource());
                object.setAccessDecisionManager(new UrlAccessDecisionManager());
                return object;
            }
        });
        registry
                .antMatchers("/hello").hasAuthority("ADMIN")
                .antMatchers("/test").anonymous();  // hello还需要权限，其他的不需要了
        super.configure(http);
    }


}
