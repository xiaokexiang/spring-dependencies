package io.spring.springsecurity.config;

import io.spring.springsecurity.config.filter.PreLoginFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @author xiaokexiang
 * @since 2021/3/8
 */
@Configuration
@EnableGlobalAuthentication
public class CommonSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserManager userManager;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userManager).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // webSecurity主要配置FilterChain、忽略路径相关
        super.configure(web);
    }

    private static final String LOGIN_PROCESS_URL = "/process";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 主要配置http请求、鉴权、过滤器相关
        http.csrf().disable()
                .cors()
                .and()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .authenticationProvider(new AuthenticationProvider() {

                    @Override
                    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                        return null;
                    }

                    @Override
                    public boolean supports(Class<?> authentication) {
                        return false;
                    }
                }) // 可以添加多个authenticationProvider，这样在ProviderManager会依次进行authenticate
                .addFilterBefore(new PreLoginFilter(LOGIN_PROCESS_URL, null), UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .loginProcessingUrl(LOGIN_PROCESS_URL) // 实际向后台提交请求的路径，此后会执行UsernamePasswordAuthenticationFilter类
                // .defaultSuccessUrl("http://www.baidu.com", false) // login页面登录成功后重定向地址（如果是successfulForwardUrl则是转发）
                .successForwardUrl("/login/success")  // 登录成功后转发的路径（可以是接口）
                .failureForwardUrl("/login/failure");  // 登录失败的时候会转发到此路径

    }
}
