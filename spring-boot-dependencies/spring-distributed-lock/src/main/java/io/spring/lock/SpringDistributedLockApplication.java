package io.spring.lock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringDistributedLockApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDistributedLockApplication.class, args);
    }

}
