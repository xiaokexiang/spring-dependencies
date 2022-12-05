package io.spring.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("/test")
public class SpringCommonApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCommonApplication.class, args);
    }

    @GetMapping
    public String hello() {
        return "hello";
    }
}
