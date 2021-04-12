package org.springframework.entity;

import lombok.Data;

/**
 * @author xiaokexiang
 * @since 2021/4/12
 */
@Data
public class Person {

    public Integer age;
    public String name;

    public Person(Integer age, String name) {
        this.age = age;
        this.name = name;
    }

    public Person() {
    }
}
