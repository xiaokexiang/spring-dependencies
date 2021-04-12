package org.springframework.chapter2;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author xiaokexiang
 * @since 2021/4/12
 * bean 生命周期
 */

public class BeanLifeCycle implements InitializingBean, DisposableBean {
    private String name;

    public BeanLifeCycle() {
        System.out.println("Construct start");
    }

    public void initMethod() {
        System.out.println("@Bean init-method()");
    }

    public void destroyMethod() {
        System.out.println("@Bean destroy-method()");
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("@PostConstruct");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("@PreDestroy");
    }

    public void setName(String name) {
        System.out.println("setter");
        this.name = name;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("DisposableBean");
    }
}
