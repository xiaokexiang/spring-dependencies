package io.spring.mybatis.mapper;

import io.spring.mybatis.entity.Department;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xiaokexiang
 * @since 2021/10/25
 */
@Repository
public interface DepartmentDao {

    List<Department> findAll();

    Department findById(String id);
}
