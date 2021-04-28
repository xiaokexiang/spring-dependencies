package org.springframework.chapter12;

import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;

/**
 * @author xiaokexiang
 * @since 2021/4/28
 */
@Service
public class DoServiceImpl implements DoService {

    @Override
    public void doSomething() throws RuntimeException {
        if (System.currentTimeMillis() % 2 == 0) {
            System.out.println("do something ...");
        } else {
            throw new RuntimeException("error");
        }
    }

    @Log
    @Override
    public Object returnSomething() {
        System.out.println("return something ...");
//        return this.get();
        ((DoServiceImpl)AopContext.currentProxy()).get(); // 调用自身方法打印多次日志的方式
        return "hello world";
    }

    // 演示被代理类中的方法调用另一个方法如何打印两遍日志
    public void get() {
        System.out.println("get ...");
    }
}
