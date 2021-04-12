package org.springframework.chapter1;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


/**
 * @author xiaokexiang
 * @since 2021/4/12
 */
@Component
@PropertySource("classpath:application.yaml")
public class InjectValueComponent {

    @Value("I'm black")
    public String black;

    // 占位符，如果是"${Component.age}"会出现NumberFormat错误，无法将String转为Integer
    // 如果是"${age}"可以成功读取，但是需要注意，如果yaml中出现多个age，那么会以最后一个为准
    @Value("${age}")
    public Integer age;

    @Override
    public String toString() {
        return "InjectValueComponent{" +
                "black='" + black + '\'' +
                ", age=" + age +
                '}';
    }
}
