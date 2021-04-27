package org.springframework.chapter9;

import org.springframework.context.ApplicationEvent;

/**
 * @author xiaokexiang
 * @since 2021/4/25
 */
public class HierarchicalEvent extends ApplicationEvent {

    public HierarchicalEvent(Object source) {
        super(source);
    }
}
