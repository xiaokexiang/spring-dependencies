package org.springframework.chapter9;

import org.springframework.context.ApplicationListener;

/**
 * @author xiaokexiang
 * @since 2021/4/25
 */
public class HierarchicalListener implements ApplicationListener<HierarchicalEvent> {

    @Override
    public void onApplicationEvent(HierarchicalEvent event) {
        System.out.println("监听到： " + event.toString());
    }
}
