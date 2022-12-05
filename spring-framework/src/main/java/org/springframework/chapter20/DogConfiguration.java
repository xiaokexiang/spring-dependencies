package org.springframework.chapter20;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class DogConfiguration {

    @Bean
    @Scope("prototype")
    public Dog dog() {
        return new Dog("旺财");
    }
}
