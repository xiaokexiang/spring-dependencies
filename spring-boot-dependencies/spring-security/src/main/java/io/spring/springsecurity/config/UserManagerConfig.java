package io.spring.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaokexiang
 * @since 2021/3/8
 */
@Configuration
public class UserManagerConfig {

    /**
     * 自定义用户管理系统
     */
    @Bean
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
}
