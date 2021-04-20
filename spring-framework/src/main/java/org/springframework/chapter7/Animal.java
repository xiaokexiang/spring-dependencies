package org.springframework.chapter7;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author xiaokexiang
 * @since 2021/4/20
 */
@Data
@Component
@PropertySource("classpath:person.properties")
public class Animal {

    @Value("${component.age}")
    private Integer age;
}
