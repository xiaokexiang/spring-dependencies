package org.springframework.chapter11;

import lombok.Data;

/**
 * @author xiaokexiang
 * @since 2021/4/27
 */
public interface Partner {

    void receiveMoney(int money);

    void playWith(String name);
}
