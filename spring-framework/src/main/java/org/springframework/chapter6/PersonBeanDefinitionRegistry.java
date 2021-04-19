package org.springframework.chapter6;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.chapter1.Person;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author xiaokexiang
 * @since 2021/4/19
 */
public class PersonBeanDefinitionRegistry implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        // 通过BeanDefinitionBuilder来创建BeanDefinition
        registry.registerBeanDefinition("person",
                BeanDefinitionBuilder.genericBeanDefinition(Person.class)
                        .addPropertyValue("name", "lucy")
                        .addPropertyValue("age", 18).getBeanDefinition());
    }
}
