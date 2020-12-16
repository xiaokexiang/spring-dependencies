package io.spring.mybatis.mapper;

import io.spring.mybatis.entity.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xiaokexiang
 * @since 2020/12/16
 */
@Repository
public interface RoleMapper {

    List<Role> select();
}
