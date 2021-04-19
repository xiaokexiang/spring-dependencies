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
@PropertySource(name = "yaml", value = "classpath:/application.yaml", factory = YamlPropertySourceFactory.class)
public class PersonYaml {
    @Value("${component.age}")
    private Integer age;
}
