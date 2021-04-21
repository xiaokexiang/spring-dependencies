package org.springframework;

import org.junit.jupiter.api.Test;
import org.springframework.chapter8.Cat;
import org.springframework.chapter8.Dog;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author xiaokexiang
 * @since 2021/4/21
 */
public class Chapter8Test {
    @Test
    void testBeanFactoryPostProcessor() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("org.springframework.chapter8");
        Dog bean = context.getBean(Dog.class);
        System.out.println(bean);
    }

    @Test
    void testBeanDefinitionRegistryPostProcessor() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("org.springframework.chapter8");
        Cat bean = context.getBean(Cat.class);
        System.out.println(bean);
    }
}
