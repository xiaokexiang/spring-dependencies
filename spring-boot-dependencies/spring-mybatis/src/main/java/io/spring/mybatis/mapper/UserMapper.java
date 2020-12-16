package io.spring.mybatis.mapper;

import io.spring.mybatis.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xiaokexiang
 * @since 2020/12/16
 */
@Mapper
@Repository
public interface UserMapper {

    @Select("SELECT * FROM user")
    List<User> select();
}
