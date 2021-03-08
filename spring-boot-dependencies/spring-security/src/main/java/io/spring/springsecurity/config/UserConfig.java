package io.spring.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaokexiang
 * @since 2021/3/8
 */
@Configuration
public class UserConfig {

    @Bean
    public UserManager userManager() {
        return new UserManager();
    }
}
