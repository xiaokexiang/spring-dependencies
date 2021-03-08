package io.spring.springsecurity.config;

import io.spring.common.response.ResponseBody;
import io.spring.springsecurity.service.UserServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import java.io.PrintWriter;

/**
 * @author xiaokexiang
 * @since 2021/2/22
 * spring security 核心配置类
 */
@Configuration
@ConditionalOnMissingBean(CommonSecurityConfig.class)
@EnableGlobalMethodSecurity(prePostEnabled = true) // 开启此注解才能使用@PreAuthorize等注解
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserServiceImpl userService;

    @Resource
    private UserManager userManager;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
         *  转换为http basic验证方式 http.httpBasic()
         *  转换为form验证的方式 http.formLogin()
         */
        http.formLogin()
                .successHandler((request, response, authentication) -> {
                    // 自定义返回体，与successForwardUrl异曲同工之妙
                    ResponseBody<String> responseBody = ResponseBody.ok("login success");
                    PrintWriter writer = response.getWriter();
                    writer.append(responseBody.toString());
                    writer.flush();
                })
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
        // 1. 使用内置的用户管理器处理，需要配置密码
//         auth.inMemoryAuthentication().passwordEncoder(passwordEncoder());
        // 2. 自定义用户验证逻辑（实现 UserDetailsService）
        // auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
        // 3. 自定义用户验证逻辑（实现 UserDetailsManager） 如果不加密不需要配置passwordEncoder
        auth.userDetailsService(userManager).passwordEncoder(passwordEncoder());
    }

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers("/static/**"); // 基于servlet filter放行某些路径
    }
}
