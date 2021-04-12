package org.springframework.chapter1;

import org.springframework.context.annotation.Primary;
import org.springframework.entity.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaokexiang
 * @since 2021/4/12
 * spring中属性注入方式
 */
@Configuration
public class InjectValueConfiguration {

    @Bean
    public Person person() {
        // 基于注解的构造器注入
        return new Person(18, "lucy.construct.annotation");
    }

    @Bean
    public Person person2() {
        // 基于注解的setter注入
        Person person = new Person();
        person.setAge(18);
        person.setName("lucy.setter.annotation");
        return person;
    }
}
