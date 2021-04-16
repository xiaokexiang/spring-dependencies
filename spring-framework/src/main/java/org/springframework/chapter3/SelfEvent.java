package org.springframework.chapter3;

import org.springframework.context.ApplicationEvent;

/**
 * @author xiaokexiang
 * @since 2021/4/16
 */
public class SelfEvent extends ApplicationEvent {
    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public SelfEvent(Object source) {
        super(source);
    }
}
