package org.springframework.chapter1;

import org.junit.jupiter.api.Test;
import org.springframework.chapter3.SelfApplicationPublisher;
import org.springframework.chapter3.SelfEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author xiaokexiang
 * @since 2021/4/16
 */
public class Chapter3Test {

    @Test
    void testInnerEvent() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("org.springframework.chapter3");
        context.start();
        @SuppressWarnings("unchecked")
        SelfApplicationPublisher<SelfEvent> publisher = (SelfApplicationPublisher<SelfEvent>) context.getBean("selfApplicationPublisher");
        publisher.push(new SelfEvent("123"));
        context.close();
    }
}
