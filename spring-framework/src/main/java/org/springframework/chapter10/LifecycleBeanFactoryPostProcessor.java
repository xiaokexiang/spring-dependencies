package org.springframework.chapter10;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.chapter8.Dog;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.util.stream.Stream;

/**
 * @author xiaokexiang
 * @since 2021/4/26
 */
public class LifecycleBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Stream.of(beanFactory.getBeanDefinitionNames()).forEach(b -> {
                    BeanDefinition beanDefinition = beanFactory.getBeanDefinition(b);
                    // 对Dog Bean进行定义修改
                    if (!StringUtils.isEmpty(beanDefinition.getBeanClassName())) {
                        if (ClassUtils.resolveClassName(beanDefinition.getBeanClassName(), this.getClass().getClassLoader()).equals(Cat.class)) {
                            beanDefinition.getPropertyValues().add("name", "wang");
                        }
                    }
                }
        );
    }
}
