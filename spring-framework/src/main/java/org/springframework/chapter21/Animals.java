package org.springframework.chapter21;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.chapter20.Dog;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:helloworld.properties")
public class Animals {

    @Autowired
    private Environment environment;

    @Bean(initMethod = "init", destroyMethod = "destroyMethod")
    @Scope("prototype")
    public Dog dog() {
        Dog dog = new Dog();
        dog.setName(environment.getProperty("hello"));
        return dog;
    }
}
