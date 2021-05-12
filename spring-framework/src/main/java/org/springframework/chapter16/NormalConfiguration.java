package org.springframework.chapter16;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author xiaokexiang
 * @since 2021/5/12
 */
@Configuration
public class NormalConfiguration {

    @Resource
    private SchoolConfiguration schoolConfiguration;

    @Bean
    public Worker worker() {
        schoolConfiguration.person();
        return new Worker();
    }


    static class Worker {
        public Worker() {
            System.out.println("worker ...");
        }
    }
}
