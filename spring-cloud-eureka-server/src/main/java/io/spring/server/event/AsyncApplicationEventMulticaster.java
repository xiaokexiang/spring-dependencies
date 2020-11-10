package io.spring.server.event;

import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;

@Component(value = "applicationEventMulticaster")
public class AsyncApplicationEventMulticaster extends SimpleApplicationEventMulticaster {

    public AsyncApplicationEventMulticaster() {
        super.setTaskExecutor(Executors.newFixedThreadPool(2));
    }
}
