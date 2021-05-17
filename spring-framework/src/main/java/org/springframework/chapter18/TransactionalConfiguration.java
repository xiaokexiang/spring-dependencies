package org.springframework.chapter18;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @author xiaokexiang
 * @since 2021/5/14
 */
@ComponentScan("org.springframework.chapter18")
@EnableTransactionManagement
public class TransactionalConfiguration {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername("bocloud");
        dataSource.setPassword("a@!#123");
        dataSource.setUrl("jdbc:mysql://172.19.18.161:3316/test?characterEncoding=utf8");
        return dataSource;
    }

    @Bean
    public TransactionManager transactionManager(DataSource dataSource) {
        // 配置类@Bean间调用，注意proxyBeanMethods
        return new DataSourceTransactionManager(dataSource);
    }
}
