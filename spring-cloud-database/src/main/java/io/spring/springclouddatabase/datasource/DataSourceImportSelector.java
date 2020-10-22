package io.spring.springclouddatabase.datasource;

import io.spring.springclouddatabase.datasource.annotation.EnableDataSource;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.core.type.AnnotationMetadata;

import java.util.List;

public class DataSourceImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        List<String> names = SpringFactoriesLoader.loadFactoryNames(EnableDataSource.class, Thread.currentThread().getContextClassLoader());
        return names.toArray(new String[0]);
    }
}
