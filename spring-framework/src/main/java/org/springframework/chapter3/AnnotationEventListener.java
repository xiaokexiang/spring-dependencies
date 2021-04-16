package org.springframework.chapter3;

import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author xiaokexiang
 * @since 2021/4/16
 */
@Component
public class AnnotationEventListener {

    @EventListener
    public void onEvent(ContextStartedEvent event) {
        System.out.println("监听到容器启动事件： " + event);
    }
}
