package io.spring.springsecurity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

/**
 * @author xiaokexiang
 * @since 2021/2/22
 * spring security 核心配置类
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) // 开启此注解才能使用@PreAuthorize等注解
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    public UserServiceImpl userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
         *  转换为http basic验证方式 http.httpBasic()
         *  转换为form验证的方式 http.formLogin()
         */
        http.formLogin().defaultSuccessUrl("http://www.baidu.com") // login页面登录成功后重定向地址（如果是successfulForwardUrl则是转发）
                .and().authorizeRequests()
                .antMatchers("/hello", "/json").access("hasAuthority('USER')") // SPEL表达式
                .antMatchers("/admin/**").access("hasAuthority('ADMIN') and hasAuthority('USER')")
                .antMatchers("/super/**").access("hasAuthority('SUPER_ADMIN')")
                .antMatchers("/test").access("@rbacService.checkPermission()") // 使用自定义类实现校验,false就需要登录
                .antMatchers("/**").authenticated() // 只要是登录用户都可以访问（不需要查验权限之类）
                .and().csrf() // 添加csrf的支持
                .and().exceptionHandling().accessDeniedHandler(new JsonAccessDeniedHandler()); // 返回json信息
        // hasRole 和 hasAuthority的区别，前者会拼接'ROLE_'前缀，后者不会
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 自定义用户验证逻辑，一般是从数据库读取，同时需要指定密码加密方式
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
