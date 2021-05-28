package io.spring.mybatis.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xiaokexiang
 * @since 2020/12/16
 */
@Data
public class Role implements Serializable {
    private Integer id;

    private String name;

    private static final long serialVersionUID = 1L;
}