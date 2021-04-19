package org.springframework;

import org.junit.jupiter.api.Test;
import org.springframework.chapter5.PersonProperties;
import org.springframework.chapter5.PersonYaml;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author xiaokexiang
 * @since 2021/4/19
 */
public class Chapter5Test {

    @Test
    void testProperties() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("org.springframework.chapter5");
        PersonProperties bean = context.getBean(PersonProperties.class);
        System.out.println(bean);
    }

    @Test
    void testYaml() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("org.springframework.chapter5");
        PersonYaml bean = context.getBean(PersonYaml.class);
        System.out.println(bean);
    }
}
