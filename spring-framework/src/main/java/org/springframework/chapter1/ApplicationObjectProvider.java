package org.springframework.chapter1;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.entity.Person;
import org.springframework.stereotype.Component;

/**
 * @author xiaokexiang
 * @see org.springframework.beans.factory.ObjectProvider
 * 延迟注入
 * @since 2021/4/12
 */
@Component
public class ApplicationObjectProvider {

    private Person p;

    public ApplicationObjectProvider(ObjectProvider<Person> person) {
        this.p = person.getIfAvailable(Person::new);
    }

    public void setPerson(ObjectProvider<Person> person) {
        this.p = person.getIfAvailable(Person::new);
    }

    @Autowired
    private ObjectProvider<Person> person;

    @Override
    public String toString() {
        // 不存在还要创建
        return "ApplicationObjectProvider{" +
                "person=" + person.getIfAvailable(Person::new) +
                '}';
    }
}
