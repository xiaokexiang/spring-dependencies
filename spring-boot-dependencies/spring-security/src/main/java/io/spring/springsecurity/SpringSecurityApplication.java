package io.spring.springsecurity;

import com.alibaba.fastjson.JSONObject;
import io.spring.springsecurity.service.MethodService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@SpringBootApplication
public class SpringSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityApplication.class, args);
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello world";
    }


    @GetMapping("/json")
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("hello", "world");
        return jsonObject;
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/super")
    public String superAdmin() {
        return "super admin";
    }

    @Resource
    public MethodService methodService;

    @GetMapping("/query")
    public String query() {
        return methodService.query();
    }

    @GetMapping("/find")
    public String find() {
        return methodService.find().getName();
    }

    @GetMapping("/delete")
    public void delete() {
        List<Integer> ids = new ArrayList<>();
        ids.add(0);
        ids.add(1);
        methodService.delete(ids);
    }

    @GetMapping("/findone")
    public List<MethodService.Person> findOne() {
        return methodService.findOne();
    }
}
