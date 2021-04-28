package org.springframework.chapter11;

import lombok.Data;

/**
 * @author xiaokexiang
 * @since 2021/4/27
 */
@Data
public class PartnerImpl implements  Partner {

    String name;

    @Override
    public void receiveMoney(int money) {
        System.out.println("get money: " + money);
    }

    @Override
    public void playWith(String name) {
        System.out.println("play with: " + name);
    }
}
