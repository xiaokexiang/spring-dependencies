package io.spring.mybatis.entity;

import lombok.Data;

/**
 * @author xiaokexiang
 * @since 2020/12/16
 */
@Data
public class User {
    private Integer id;
    private String name;
    private Integer roleId;
}
