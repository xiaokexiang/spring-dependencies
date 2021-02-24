package io.spring.springsecurity.config;

import io.spring.springsecurity.service.UserServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * 自定义用户管理系统
     */
//    @Bean
    public UserDetailsManager userDetailsManager() {
        UserManager userManager = new UserManager();
        userManager.createUser(innerUser());
        return userManager;
    }

    private UserDetails innerUser() {
        // load user by username 模拟从数据库获取用户权限等信息
        List<GrantedAuthority> authorities = new ArrayList<>();
        // 添加 ADMIN & USER 权限
        authorities.add(new SimpleGrantedAuthority("USER"));
        authorities.add(new SimpleGrantedAuthority("ADMIN"));
        // 一般数据库用户密码存入时会先加密，此处只是模拟加密后的用户信息
        // 使用UserDetails.User$UserBuilder构建user
        return User.withUsername("jack")
                .passwordEncoder(new BCryptPasswordEncoder()::encode)
                .password("jack") // 如果不开启加密，那么需要去除passwordEncoder，密码变成"{noop}jack"
                // AuthorityUtils.NO_AUTHORITIES
                .authorities(authorities)
                .build();
    }

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
        // 1. 使用内置的用户管理器处理，需要配置密码
        // auth.inMemoryAuthentication().passwordEncoder(passwordEncoder());
        // 2. 自定义用户验证逻辑（实现 UserDetailsService）
        // auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
        // 3. 自定义用户验证逻辑（实现 UserDetailsManager） 如果不加密不需要配置passwordEncoder
        auth.userDetailsService(userDetailsManager()).passwordEncoder(passwordEncoder());
    }

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
