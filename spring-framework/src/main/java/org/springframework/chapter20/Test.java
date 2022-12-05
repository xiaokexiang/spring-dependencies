package org.springframework.chapter20;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Test {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Dog.class, DogBeanPostProcessor.class);
        Dog dog = context.getBean(Dog.class);
        context.close();
    }
}
