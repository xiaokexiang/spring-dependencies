package org.springframework.chapter12;

/**
 * @author xiaokexiang
 * @since 2021/4/28
 */
public interface DoService {

    void doSomething() throws RuntimeException;

    Object returnSomething();
}
