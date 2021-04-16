package org.springframework.chapter1;

import org.junit.jupiter.api.Test;
import org.springframework.chapter4.Boss;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

/**
 * @author xiaokexiang
 * @since 2021/4/16
 */
public class Chapter4Test {
    @Test
    void enableImport() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext("org.springframework.chapter4");
        Arrays.stream(applicationContext.getBeanDefinitionNames()).forEach(System.out::println);
    }
}
