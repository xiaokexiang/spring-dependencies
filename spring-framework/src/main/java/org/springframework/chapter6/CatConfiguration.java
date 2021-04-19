package org.springframework.chapter6;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaokexiang
 * @since 2021/4/19
 */
@Configuration
public class CatConfiguration {

    @Bean
    public Cat cat() {
        return new Cat();
    }

    static class Cat {
    }
}
