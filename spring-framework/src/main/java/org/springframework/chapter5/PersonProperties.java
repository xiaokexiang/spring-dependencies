package org.springframework.chapter5;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author xiaokexiang
 * @since 2021/4/19
 */
@Data
@Configuration
@PropertySource(value = "classpath:/person.properties")
public class PersonProperties {

    // 默认jdk内置的Properties是无法解析yaml文件的
    @Value("${component.age}")
    private Integer age;
}
