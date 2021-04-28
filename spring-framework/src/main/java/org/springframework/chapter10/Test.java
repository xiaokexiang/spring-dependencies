package org.springframework.chapter10;

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;

/**
 * @author xiaokexiang
 * @since 2021/4/28
 */
public class Test {
    public static void main(String[] args) {

        LifecycleNameReadPostProcessor beanPostProcessor = new LifecycleNameReadPostProcessor();
        System.out.println(beanPostProcessor instanceof BeanPostProcessor); // true
        System.out.println(beanPostProcessor instanceof DestructionAwareBeanPostProcessor); // false

        LifecycleDestructionPostProcessor lifecycleDestructionPostProcessor = new LifecycleDestructionPostProcessor();
        System.out.println(lifecycleDestructionPostProcessor instanceof BeanPostProcessor); // true
        System.out.println(lifecycleDestructionPostProcessor instanceof DestructionAwareBeanPostProcessor); // true
    }
}
