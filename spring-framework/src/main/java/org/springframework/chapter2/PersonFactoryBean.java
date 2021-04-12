package org.springframework.chapter2;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.entity.Person;
import org.springframework.stereotype.Component;

/**
 * @author xiaokexiang
 * @since 2021/4/12
 */
@Component
public class PersonFactoryBean implements FactoryBean<Person> {
    @Override
    public Person getObject() throws Exception {
        return new Person(28, "nothing");
    }

    @Override
    public Class<?> getObjectType() {
        return Person.class;
    }
}
