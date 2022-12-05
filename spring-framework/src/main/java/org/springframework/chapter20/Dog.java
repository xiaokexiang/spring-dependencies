package org.springframework.chapter20;


import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class Dog implements InitializingBean, DisposableBean {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        System.out.println("set name ...");
        this.name = name;
    }

    public Dog(String name) {
        System.out.println("dog create ...");
        this.name = name;
    }

    public Dog() {
        System.out.println("dog create ...");
    }

    public void init() {
        System.out.println("dog init method ...");
    }

    public void destroyMethod() {
        System.out.println("dog destroy method ...");
    }

    @PostConstruct
    public void construct() {
        System.out.println("dog construct ...");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("dog preDestroy ...");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("dog afterPropertiesSet ...");
    }

    public void destroy() {
        System.out.println("dog destroy ...");
    }

}
