package org.springframework.chapter19;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author xiaokexiang
 * @since 2021/5/28
 */
@Component
public class Cat {

    @Value("${component.age}")
    private String age;

    @Autowired
    private Person person;
}
