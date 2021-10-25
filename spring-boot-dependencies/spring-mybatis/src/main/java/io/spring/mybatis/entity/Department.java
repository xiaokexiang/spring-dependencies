package io.spring.mybatis.entity;

import lombok.Data;

import java.util.Set;

/**
 * @author xiaokexiang
 * @since 2021/10/25
 */
@Data
public class Department {
    private String id;

    private String name;

    private String tel;

    private Set<User> users;

    @Override
    public String toString() {
        return "Department{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", tel='" + tel + '\'' +
                ", users=" + users +
                '}';
    }
}
