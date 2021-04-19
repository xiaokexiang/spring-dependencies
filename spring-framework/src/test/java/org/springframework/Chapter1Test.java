package org.springframework;

import org.springframework.chapter1.*;
import org.springframework.chapter1.Person;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author xiaokexiang
 * @since 2021/4/12
 */
public class Chapter1Test {
    @Test
    public void testAnnotation() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(InjectValueConfiguration.class);
        Person person = (Person) applicationContext.getBean("person");
        System.out.println(person);
    }

    /* 基于@Value注解的属性注入*/
    @Test
    public void testAnnotationValue() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext("org.springframework.chapter1");
        InjectValueComponent injectValueComponent = applicationContext.getBean(InjectValueComponent.class);
        System.out.println(injectValueComponent);
    }

    @Test
    public void testAnnotationSpel() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext("org.springframework.chapter1");
        InjectValueSpel injectValueSpel = applicationContext.getBean(InjectValueSpel.class);
        System.out.println(injectValueSpel);
    }


    @Test
    public void testXml() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application.xml");
        Person person = (Person) applicationContext.getBean("person");
        System.out.println(person);
    }

    @Test
    public void testAutowired() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext("org.springframework.chapter1");
        InjectAutowired bean = applicationContext.getBean(InjectAutowired.class);
        System.out.println(bean);
    }

    @Test
    public void testAutowiredQualifier() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext("org.springframework.chapter1");
        // 因为容器里面存在两个Person的bean
        Person bean = applicationContext.getBean(Person.class);
        System.out.println(bean);
    }

    @Test
    public void testAware() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext("org.springframework.chapter1");
        // 因为容器里面存在两个Person的bean
        ApplicationAware aware = applicationContext.getBean(ApplicationAware.class);
        System.out.println(aware);
    }
}
