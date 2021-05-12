package org.springframework.chapter16;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaokexiang
 * @since 2021/5/12
 */
@Configuration
public class SchoolConfiguration {

    @Bean
    public Person person() {
        return new Person();
    }

    @Bean
    public Student student() {
        person();
        return new Student();
    }

    @Data
    static class Person {

        public Person() {
            System.out.println("person ...");
        }
    }

    @Data
    static class Student {
        public Student() {
            System.out.println("student ...");
        }
    }
}
