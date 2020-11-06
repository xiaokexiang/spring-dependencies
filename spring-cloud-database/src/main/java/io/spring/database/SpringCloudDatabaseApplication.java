package io.spring.database;

import io.spring.database.datasource.annotation.EnableDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDataSource
@SpringBootApplication
public class SpringCloudDatabaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudDatabaseApplication.class, args);
    }

}
