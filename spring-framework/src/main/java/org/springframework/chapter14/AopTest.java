package org.springframework.chapter14;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author xiaokexiang
 * @since 2021/4/29
 */
public class AopTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext("org.springframework.chapter14");
        UserService userService = ctx.getBean(UserService.class);
        userService.get("abc");
    }
}
