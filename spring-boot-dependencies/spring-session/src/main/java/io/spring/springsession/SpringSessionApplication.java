package io.spring.springsession;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@SpringBootApplication
@EnableRedisHttpSession // 注入RedisSessionRepository，用于存放session数据到redis
public class SpringSessionApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSessionApplication.class, args);
    }


    @GetMapping("/index")
    public String getSession(HttpServletRequest request) {
        /*
         * spring-session 无感替换默认的getSession，核心在于 SessionRepositoryFilter & @EnableRedisHttpSession
         * 注入FilterChain链中，通过包装默认的request和response来实现替换
         * getSession()返回的是HttpSessionWrapper，执行的例如setAttribute()也是spring-session的方法
         * try {
         * } finally {
         *   wrappedRequest.commitSession();
         * }
         * 最终调用commitSession()方法将session写入response且保存到redis中(默认保存30min)
         */
        HttpSession session = request.getSession();
        session.setAttribute("hello", "world");
        return session.getId();
    }
}
