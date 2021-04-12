package org.springframework.chapter2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaokexiang
 * @since 2021/4/12
 */
@Configuration
public class BeanLifeCycleConfiguration {

    @Bean(initMethod = "initMethod", destroyMethod = "destroyMethod")
    public BeanLifeCycle beanLifeCycle() {
        BeanLifeCycle beanLifeCycle = new BeanLifeCycle();
        beanLifeCycle.setName("123");
        return beanLifeCycle;
    }
}
