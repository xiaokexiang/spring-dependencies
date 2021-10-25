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

    // 只查询user信息，不包含部门
    List<User> findAll();

    List<User> findAllLazy();

    List<User> findAllByDepartmentId();

    List<User> findAllUseTypeHandler();
}
