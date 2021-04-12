package org.springframework.chapter1;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author xiaokexiang
 * @since 2021/4/12
 */
@Component
public class InjectValueSpel {

    // SPEL表达式
    @Value("#{person.age + 1}")
    public Integer age;

    // 字符串拼接
    @Value("#{'copy from ' + person.name}")
    public String name;

    // 调用类的属性
    @Value("#{T(Integer).MAX_VALUE}")
    public Integer value;

    @Value("#{person.name.substring(5)}")
    public String substr;

    @Override
    public String toString() {
        return "InjectValueSpel{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", value=" + value +
                ", substr='" + substr + '\'' +
                '}';
    }
}
