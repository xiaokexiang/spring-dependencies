package io.spring.mybatis.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author xiaokexiang
 * @since 2020/12/16
 */
@Data
public class User {
    private String id;

    private String name;

    private Integer age;

    private Date birthday;

    private Department department;

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", birthday=" + birthday +
                ", department=" + department +
                '}';
    }
}
