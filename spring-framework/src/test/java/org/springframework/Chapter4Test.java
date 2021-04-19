package org.springframework;

import org.junit.jupiter.api.Test;
import org.springframework.chapter1.Person;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

import javax.xml.transform.Source;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * @author xiaokexiang
 * @since 2021/4/16
 */
public class Chapter4Test {
    @Test
    void enableImport() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext("org.springframework.chapter4");
        Arrays.stream(applicationContext.getBeanDefinitionNames()).forEach(System.out::println);
    }

    // Introspector 对bean的默认属性的处理规则
    @Test
    void decapitalize() {
        System.out.println(Introspector.decapitalize("URL")); // URL
        System.out.println(Introspector.decapitalize("Abc")); // abc
        System.out.println(Introspector.decapitalize("HellO")); // hellO
    }

    // 获取bean的相关信息
    @Test
    public void getBeanInfo() throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(Person.class, 3);
        System.out.println(beanInfo);
    }

    // 默认先在src/test/resources目录下找，找不到就去src/main/resources下找
    @Test
    void classPathResource() throws IOException {
        ClassPathResource resource = new ClassPathResource("application.yaml");
        File file = resource.getFile();
        System.out.println(file);
    }

    @Test
    void fileSystemResource() {
        FileSystemResource systemResource = new FileSystemResource("src/main/resources/application.yaml");
        File file = systemResource.getFile();
        System.out.println(file);
    }
}
