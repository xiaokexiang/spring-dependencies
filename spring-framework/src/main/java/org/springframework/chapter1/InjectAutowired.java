package org.springframework.chapter1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xiaokexiang
 * @see org.springframework.beans.factory.annotation.Autowired
 * 基于@Autowired的依赖注入
 * @since 2021/4/12
 */
@Component
public class InjectAutowired {

    // 这里的名字有技巧，如果存在多个类型相同的bean，那么回去匹配变量名，存在相同的就注入，否则就No qualifying bean
    @Autowired
    Person person;

    @Autowired
    List<Person> personList; // 将多个类型相同的bean注入为list

    @Autowired
    public InjectAutowired(Person person) { // 构造注入的变量名：person需要注意
        this.person = person;
    }

    @Autowired
    public void setPerson(Person person) { // setter注入的变量名：person需要注意
        this.person = person;
    }

    @Override
    public String toString() {
        return "InjectAutowired{" +
                "person=" + person +
                ", personList=" + personList +
                '}';
    }
}
