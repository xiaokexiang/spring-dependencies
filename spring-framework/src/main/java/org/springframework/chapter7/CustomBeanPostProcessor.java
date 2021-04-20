package org.springframework.chapter7;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.chapter1.Person;
import org.springframework.stereotype.Component;

/**
 * @author xiaokexiang
 * @since 2021/4/20
 */
@Component
public class CustomBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("before: " + bean);
        if (bean instanceof Animal) {
            bean = new Person();
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("after: " + bean);
        return bean;
    }
}
