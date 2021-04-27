package org.springframework.chapter9;

import org.springframework.context.ApplicationListener;
import org.springframework.context.PayloadApplicationEvent;

/**
 * @author xiaokexiang
 * @since 2021/4/25
 */
public class PayloadApplicationEventListener implements ApplicationListener<PayloadApplicationEvent<Integer>> {
    @Override
    public void onApplicationEvent(PayloadApplicationEvent<Integer> event) {
        System.out.println("监听到指定泛型的PayLoadEvent： " + event.toString());
    }
}
