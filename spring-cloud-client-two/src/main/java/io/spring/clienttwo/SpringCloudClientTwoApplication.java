package io.spring.clienttwo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableDiscoveryClient
@SpringBootApplication
public class SpringCloudClientTwoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudClientTwoApplication.class, args);
    }

    @RequestMapping("/index")
    public String index() {
        return "Hello! My name is client-two!";
    }
}
