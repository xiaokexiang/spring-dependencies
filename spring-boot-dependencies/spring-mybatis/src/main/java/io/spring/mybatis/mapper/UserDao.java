package io.spring.mybatis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import io.spring.mybatis.entity.User;

import java.util.List;

/**
 * @author xiaokexiang
 * @since 2020/12/16
 */
@Mapper
@Repository
public interface UserDao {

    @Select("SELECT * FROM user")
    List<User> select();
}
