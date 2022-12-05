package org.springframework.chapter20;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelloFactory implements FactoryBean<Dog> {

    private ObjectProvider<Dog> dog;

    @Autowired
    public HelloFactory(ObjectProvider<Dog> dog) {
        this.dog = dog;
    }

    @Override
    public Dog getObject() {
        return dog.getIfAvailable(() -> new Dog("旺财"));
    }

    @Override
    public Class<?> getObjectType() {
        return Dog.class;
    }

    public HelloFactory() {
        System.out.println("HelloFactory初始化创建了。。。");
    }
}
