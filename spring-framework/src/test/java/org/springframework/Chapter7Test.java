package org.springframework;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author xiaokexiang
 * @since 2021/4/20
 */
public class Chapter7Test {

    @Test
    void testBeanPostProcessor() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("org.springframework.chapter7");
    }
}
