package io.spring.mybatis.config;

import io.spring.mybatis.entity.Department;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author xiaokexiang
 * @since 2021/10/25
 * 可以对prepareStatement的参数进行设置 & 对返回ResultSet进行修改
 */
public class DepartmentTypeHandler extends BaseTypeHandler<Department> {
    @Override
    public void setParameter(PreparedStatement ps, int i, Department parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getId());
    }

    @Override
    public Department getResult(ResultSet rs, String columnName) throws SQLException {
        Department department = new Department();
        department.setId(rs.getString(columnName));
        return department;
    }

    @Override
    public Department getResult(ResultSet rs, int columnIndex) throws SQLException {
        Department department = new Department();
        department.setId(rs.getString(columnIndex));
        return department;
    }

    @Override
    public Department getResult(CallableStatement cs, int columnIndex) throws SQLException {
        Department department = new Department();
        department.setId(cs.getString(columnIndex));
        return department;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Department parameter, JdbcType jdbcType) throws SQLException {

    }

    @Override
    public Department getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return null;
    }

    @Override
    public Department getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return null;
    }

    @Override
    public Department getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return null;
    }
}
