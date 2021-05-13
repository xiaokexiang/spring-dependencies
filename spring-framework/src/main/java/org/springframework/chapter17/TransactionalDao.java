package org.springframework.chapter17;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author xiaokexiang
 * @since 2021/5/13
 */
@Repository
public class TransactionalDao {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public void insert(Integer money, Integer id) {
        jdbcTemplate.execute(String.format("update tbl_account set money = %d where id = %d", money, id));
    }
}
