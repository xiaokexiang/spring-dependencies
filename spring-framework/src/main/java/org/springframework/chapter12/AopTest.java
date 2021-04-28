package org.springframework.chapter12;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author xiaokexiang
 * @since 2021/4/28
 */
public class AopTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("org.springframework.chapter12");
        DoService bean = context.getBean(DoService.class);
        bean.returnSomething();
    }
}
