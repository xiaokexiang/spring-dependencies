package io.spring.network.netty.eventloop;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author xiaokexiang
 * @see ScheduledThreadPoolExecutor#scheduleAtFixedRate(Runnable, long, long, TimeUnit)
 * 1s -> 1s + (task time + delay time)s -> 1s + 2*(task time + delay time)s -> ...
 * @see ScheduledThreadPoolExecutor#scheduleWithFixedDelay(Runnable, long, long, TimeUnit)
 * 1s -> 1s + (task time)s -> 1s + 2*(task time)s -> ...
 * @since 2020/12/9
 */
@Slf4j
public class ScheduleServiceTest {

    public static void execDelay() {
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(2);
        executorService.scheduleWithFixedDelay(() -> {
            log.info("scheduleWithFixedDelay time: " + new Date());
            try {
                Thread.sleep(6_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 5L, 5L, TimeUnit.SECONDS);
        // 10:30:00 -> 10:30:11 -> 10:30:22 -> ...
    }

    public static void execRate() {
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(2);
        executorService.scheduleAtFixedRate(() -> {
            log.info("scheduleAtFixedRate time: " + new Date());
            try {
                Thread.sleep(6_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 5L, 5L, TimeUnit.SECONDS);
        // 10:30:00 -> 10:30:06 -> 10:30:12 ->...
    }

    public static void main(String[] args) throws InterruptedException {
//        execDelay();
        execRate();
    }
}
