package org.springframework.chapter3;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author xiaokexiang
 * @since 2021/4/16
 */
@Component
public class SelfApplicationListener implements ApplicationListener<SelfEvent> {

    @Override
    public void onApplicationEvent(SelfEvent event) {
        System.out.println("监听自定义事件： " + event);
    }
}
