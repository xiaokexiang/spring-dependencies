package org.springframework;

import org.junit.jupiter.api.Test;
import org.springframework.chapter9.HierarchicalEvent;
import org.springframework.chapter9.HierarchicalListener;
import org.springframework.chapter9.PayloadApplicationEventListener;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author xiaokexiang
 * @since 2021/4/25
 * 父子容器与高级事件传播
 */
public class Chapter9Test {
    /**
     * 父子容器事件传播
     */
    @Test
    void HierarchicalApplicationEvent() {
        AnnotationConfigApplicationContext parent = new AnnotationConfigApplicationContext();
        AnnotationConfigApplicationContext child = new AnnotationConfigApplicationContext();
        parent.addApplicationListener(new HierarchicalListener());
        child.addApplicationListener(new HierarchicalListener());
        child.setParent(parent);
        parent.refresh();
        child.refresh();
        parent.publishEvent(new HierarchicalEvent("父容器发送事件"));
        child.publishEvent(new HierarchicalEvent("子容器发送事件"));
//        监听到： org.springframework.chapter9.HierarchicalEvent[source=父容器发送事件]
//        监听到： org.springframework.chapter9.HierarchicalEvent[source=子容器发送事件]
//        监听到： org.springframework.chapter9.HierarchicalEvent[source=子容器发送事件]
    }

    @Test
    void payloadEvent() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.addApplicationListener(new PayloadApplicationEventListener());
        context.refresh();
        // 默认是会监听到所有的事件，但是指定泛型后只会监听到指定泛型的事件
        // 我们发送的事件类型不是继承自ApplicationEvent，这点需要注意
        context.publishEvent(123);
    }
}
