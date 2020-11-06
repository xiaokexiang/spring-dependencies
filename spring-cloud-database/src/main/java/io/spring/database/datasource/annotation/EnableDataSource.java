package io.spring.database.datasource.annotation;


import io.spring.database.datasource.DataSourceImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 数据源自动注入
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(DataSourceImportSelector.class)
public @interface EnableDataSource {
}
