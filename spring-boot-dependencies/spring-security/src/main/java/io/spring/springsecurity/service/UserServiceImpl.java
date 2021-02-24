package io.spring.springsecurity.service;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaokexiang
 * @since 2021/2/22
 */
@Configuration
public class UserServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // load user by username 模拟从数据库获取用户权限等信息
        List<GrantedAuthority> authorities = new ArrayList<>();
        // 添加 ADMIN & USER 权限
        authorities.add(new SimpleGrantedAuthority("USER"));
        authorities.add(new SimpleGrantedAuthority("ADMIN"));
        // 一般数据库用户密码存入时会先加密，此处只是模拟加密后的用户信息
        // 使用UserDetails.User$UserBuilder构建user
        return User.withUsername("admin")
                .passwordEncoder(new BCryptPasswordEncoder()::encode)
                .password("admin")
                // AuthorityUtils.NO_AUTHORITIES
                .authorities(authorities)
                .build();
    }
}
