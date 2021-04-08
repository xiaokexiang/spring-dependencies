package io.spring.network.netty.eventloop;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author xiaokexiang
 * @see ScheduledThreadPoolExecutor#scheduleAtFixedRate(Runnable, long, long, TimeUnit)
 * 如果线程执行时间 < period，每个线程执行相差period
 * 如果线程执行时间 > period，每个线程执行相差线程的执行时间
 * @see ScheduledThreadPoolExecutor#scheduleWithFixedDelay(Runnable, long, long, TimeUnit)
 * 每个线程执行相差 (delay time + task time)
 * @since 2020/12/9
 */
@Slf4j
public class ScheduleServiceTest {

    public static void execDelay() {
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(2);
        executorService.scheduleWithFixedDelay(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + " Start: scheduleWithFixedDelay: " + new Date());
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " End  : scheduleWithFixedDelay: " + new Date());
        }, 2L, 2L, TimeUnit.SECONDS);
    }

    public static void execRate() {
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);
        executorService.scheduleAtFixedRate(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + " Start: scheduleAtFixedRate:  " + new Date());
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " End  : scheduleAtFixedRate:    " + new Date());
        }, 2L, 3L, TimeUnit.SECONDS);
    }

    public static void main(String[] args) throws InterruptedException {
        execDelay();
//        execRate();
    }
}
