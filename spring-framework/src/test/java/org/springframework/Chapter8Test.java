package org.springframework;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.chapter1.Person;
import org.springframework.chapter8.Cat;
import org.springframework.chapter8.Dog;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;
import java.util.Set;

/**
 * @author xiaokexiang
 * @since 2021/4/21
 */
public class Chapter8Test {
    @Test
    void testBeanFactoryPostProcessor() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("org.springframework.chapter8");
        Dog bean = context.getBean(Dog.class);
        System.out.println(bean);
    }

    @Test
    void testBeanDefinitionRegistryPostProcessor() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("org.springframework.chapter8");
        Cat bean = context.getBean(Cat.class);
        System.out.println(bean);
    }

    /**
     * 编程驱动IOC，了解代码是如何注入的
     */
    @Test
    void programDriver() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.registerBeanDefinition("person",
                BeanDefinitionBuilder.genericBeanDefinition(Person.class)
                        .addPropertyValue("name", "lucy")
                        .addPropertyValue("age", 18)
                        .getBeanDefinition());
        context.refresh(); // refresh 方法的执行，会触发非延迟加载的单实例 bean 的实例化和初始化。
        Person bean = context.getBean(Person.class);
        System.out.println(bean);
    }

    @Test
    void packageScanner() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(context);
        // 不使用默认的规则
        scanner.resetFilters(false);
        // 额外的扫描规则(因为去除了默认的,所以只能扫描到单实例的cat)
        scanner.addIncludeFilter((metadataReader, metadataReaderFactory) -> metadataReader.getClassMetadata().getClassName().equals(Cat.class.getName()));
        // 返回成功扫描的数量
        int scan = scanner.scan("org.springframework.chapter8");
        System.out.println(scan);
        Set<BeanDefinition> components = scanner.findCandidateComponents("org.springframework.chapter8");
        components.forEach(c -> System.out.println(c.getBeanClassName()));
    }
}
