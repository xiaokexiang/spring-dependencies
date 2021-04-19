package org.springframework;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.chapter6.CatConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author xiaokexiang
 * @since 2021/4/19
 */
public class Chapter6Test {
    @Test
    void getBeanDefinition() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("org.springframework.chapter6");
        BeanDefinition catConfiguration = context.getBeanFactory().getBeanDefinition("cat");
        System.out.println(catConfiguration);
        System.out.println(catConfiguration.getClass().getName()); // ConfigurationClassBeanDefinition
    }

    @Test
    void getBeanDefinition2() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CatConfiguration.class);
        BeanDefinition catConfiguration = context.getBeanFactory().getBeanDefinition("cat");
        System.out.println(catConfiguration);
        System.out.println(catConfiguration.getClass().getName()); // ConfigurationClassBeanDefinition
    }

    @Test
    void addBeanByBeanDefinition() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("org.springframework.chapter6");
        BeanDefinition person = context.getBeanFactory().getBeanDefinition("person");
        System.out.println(person);
    }
}
