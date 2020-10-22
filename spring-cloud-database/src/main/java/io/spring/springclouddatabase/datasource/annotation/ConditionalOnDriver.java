package io.spring.springclouddatabase.datasource.annotation;

import io.spring.springclouddatabase.datasource.ConditionalOnDriverName;
import io.spring.springclouddatabase.datasource.DriverType;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 根据classpath下的jar包决定加载的数据源
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Import(ConditionalOnDriverName.class)
public @interface ConditionalOnDriver {
    DriverType value() default DriverType.MYSQL;
}
