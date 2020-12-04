package io.spring.server.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MyListener2 {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @EventListener
    public void onApplicationEvent(MyEvent event) {
        logger.info("MyListener2 get event: {}", event.getSource());
    }
}
