package org.springframework.chapter18;

import org.springframework.chapter13.JdbcUtils;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author xiaokexiang
 * @since 2021/4/28
 */
@Repository
public class FinanceDao {

    public void addMoney(Long id, int money) {
        try {
            Connection connection = JdbcUtils.getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("update tbl_employee set salary = salary + ? where id = ?");
            preparedStatement.setInt(1, money);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void subtractMoney(Long id, int money) {
        try {
            Connection connection = JdbcUtils.getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("update tbl_employee set salary = salary - ? where id = ?");
            preparedStatement.setInt(1, money);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
