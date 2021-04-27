package org.springframework.chapter10;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

/**
 * @author xiaokexiang
 * @since 2021/4/25
 */
@Configuration
@ComponentScan("org.springframework.chapter10")
@PropertySource("classpath:person.properties")
public class LifecycleSourceConfiguration {

    @Value("${component.age}")
    public Integer age;

    @Bean(initMethod = "initMethod", destroyMethod = "destroyMethod")
    public Person person() {
        Person person = new Person();
        person.setName("lucy");
        return person;
    }
}
