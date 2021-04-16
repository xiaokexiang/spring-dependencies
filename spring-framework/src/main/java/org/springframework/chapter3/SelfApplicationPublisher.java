package org.springframework.chapter3;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

/**
 * @author xiaokexiang
 * @since 2021/4/16
 */
@Component
public class SelfApplicationPublisher<T extends ApplicationEvent> implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void push(T t) {
        applicationEventPublisher.publishEvent(t);
    }
}
