package io.spring.event;

import io.spring.event.event.MyEvent;
import io.spring.event.event.MyPublisher;
import io.spring.event.interceptors.LoginInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@RestController
@SpringBootApplication
public class EventApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(EventApplication.class, args);
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
