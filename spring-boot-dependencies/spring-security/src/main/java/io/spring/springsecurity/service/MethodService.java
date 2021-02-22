package io.spring.springsecurity.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author xiaokexiang
 * @since 2021/2/22
 * 常见的4种方法级的注解使用
 */
@Service
public class MethodService {

    // 虽然配置文件中将/query设为只需登录就能访问的url，但此处加上了权限就会验证权限
    @PreAuthorize("hasAuthority('QUERY')")
    public String query() {
        return "query success";
    }

    // 当条件满足时才会返回否则会抛出异常
    @PostAuthorize("returnObject.name == 'test'") // 判断当前登录用户名是否为test
    public Person find() {
        return new Person(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    // 会将传入的参数按照条件过滤
    @PreFilter(filterTarget = "ids", value = "filterObject%2==0")
    public void delete(List<Integer> ids) {
        // 只保留偶数的id
        Stream.of(ids).forEach(System.out::println);
    }

    // 过滤返回的数据中不符合条件的，zhangsan会被过滤掉
    @PostFilter("filterObject.name == authentication.name")
    public List<Person> findOne() {
        List<Person> persons = new ArrayList<>();
        persons.add(new Person("zhangsan"));
        persons.add(new Person("admin"));
        return persons;
    }


    @Data
    @AllArgsConstructor
    public static class Person {
        String name;
    }
}
