package io.spring.springclouddatabase.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import io.spring.springclouddatabase.datasource.annotation.ConditionalOnDriver;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;


public class MysqlConfiguration extends AbstractDataSourceConfiguration {

    @Bean
    @ConditionalOnDriver
    public DataSource dataSource() {
        DruidDataSource source = new DruidDataSource();
        source.setUsername(environment.getProperty("spring.datasource.username"));
        source.setPassword(environment.getProperty("spring.datasource.password"));
        source.setUrl(environment.getProperty("spring.datasource.url"));
        source.setDriverClassName(DriverType.MYSQL.getDriver());
        return source;
    }
}
