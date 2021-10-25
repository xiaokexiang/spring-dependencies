package io.spring.mybatis.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author xiaokexiang
 * @since 2021/10/25
 */
@Configuration
public class MybatisConfiguration {

    @Resource
    DataSource dataSource;

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setObjectFactory(new CustomObjectFactory());
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/**/*.xml"));
        factoryBean.setDataSource(dataSource);
        factoryBean.setTypeHandlers(new DepartmentTypeHandler());
        return factoryBean.getObject();
    }
}
