package org.springframework.chapter21;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Test {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Animals.class);

        Object dog = context.getBean("dog");
        context.getBeanFactory().destroyBean(dog);
    }
}
