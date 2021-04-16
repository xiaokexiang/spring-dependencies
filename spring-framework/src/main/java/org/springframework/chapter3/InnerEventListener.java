package org.springframework.chapter3;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;


/**
 * @author xiaokexiang
 * @since 2021/4/16
 */
@Component
public class InnerEventListener implements ApplicationListener<ContextClosedEvent> {

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        System.out.println("监听到容器关闭事件： " + event);
    }
}
