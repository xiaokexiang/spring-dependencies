package org.springframework.chapter17;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author xiaokexiang
 * @since 2021/5/13
 */
public class TransactionalTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("org.springframework.chapter17");
        TransactionalService bean = context.getBean(TransactionalService.class);
        bean.step1();
    }
}
