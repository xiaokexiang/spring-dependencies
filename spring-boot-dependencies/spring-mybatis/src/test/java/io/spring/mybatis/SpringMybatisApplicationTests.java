package io.spring.mybatis;

import io.spring.mybatis.entity.Department;
import io.spring.mybatis.entity.User;
import io.spring.mybatis.mapper.DepartmentDao;
import io.spring.mybatis.mapper.UserDao;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class SpringMybatisApplicationTests {

    @Test
    void contextLoads() {
    }

    @Resource
    private UserDao userDao;

    @Resource
    private DepartmentDao departmentDao;

    @Test
    void selectUser() {
        List<User> users = userDao.findAll();
        users.forEach(u -> System.out.println(u.toString()));
    }

    @Test
    void selectUserLazy() {
        List<User> allLazy = userDao.findAllLazy();
        allLazy.forEach(u -> System.out.println(u.toString()));
    }

    @Test
    void selectUserUseTypeHandler() {
        List<User> users = userDao.findAllUseTypeHandler();
        users.forEach(u -> System.out.println(u.toString()));
    }

    @Test
    void selectDepartment() {
        List<Department> departments = departmentDao.findAll();
        departments.forEach(u -> System.out.println(u.toString()));
    }
}
