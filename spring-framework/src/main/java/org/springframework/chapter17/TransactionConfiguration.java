package org.springframework.chapter17;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @author xiaokexiang
 * @since 2021/5/13
 */
@Configuration
@EnableAspectJAutoProxy(exposeProxy = true)
@EnableTransactionManagement(proxyTargetClass = true)
public class TransactionConfiguration {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername("bocloud");
        dataSource.setPassword("123$abc");
        dataSource.setUrl("jdbc:mysql://10.50.8.34:3316/test?characterEncoding=utf8");
        return dataSource;
    }

    @Bean
    public TransactionManager transactionManager() {
        // 配置类@Bean间调用，注意proxyBeanMethods
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}
