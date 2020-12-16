package io.spring.mybatis;

import io.spring.mybatis.entity.Role;
import io.spring.mybatis.entity.User;
import io.spring.mybatis.mapper.RoleMapper;
import io.spring.mybatis.mapper.UserMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@MapperScan
@RestController
@SpringBootApplication
public class SpringMybatisApplication {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RoleMapper roleMapper;

    @GetMapping("/users")
    public List<User> selectUsers() {
        return userMapper.select();
    }

    @GetMapping("/roles")
    public List<Role> selectRoles() {
        return roleMapper.select();
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringMybatisApplication.class, args);
    }
}
