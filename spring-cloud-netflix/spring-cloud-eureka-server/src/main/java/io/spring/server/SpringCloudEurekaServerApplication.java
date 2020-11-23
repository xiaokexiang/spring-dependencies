package io.spring.server;

import io.spring.server.event.MyEvent;
import io.spring.server.event.MyPublisher;
import io.spring.server.interceptors.LoginInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@RestController
@EnableEurekaServer
@SpringBootApplication
public class SpringCloudEurekaServerApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudEurekaServerApplication.class, args);
    }

    @GetMapping("/index")
    public String index() {
        return "hello world";
    }

    @GetMapping("/event")
    public void publish() {
        myPublisher.publish(new MyEvent("hello world"));
    }

    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor()).addPathPatterns("/**");
    }

    @Resource
    private MyPublisher myPublisher;
}
