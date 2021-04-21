package org.springframework.chapter8;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.util.stream.Stream;

/**
 * @author xiaokexiang
 * @since 2021/4/21
 * BeanDefinitionRegistry后置处理器先于BeanFactoryPostProcessor
 */
@Component
public class CustomBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        if (!registry.containsBeanDefinition("cat")) {
            // 构造BeanDefinition，并注册进BeanFactory
            BeanDefinition dogDefinition = BeanDefinitionBuilder.genericBeanDefinition(Cat.class).getBeanDefinition();
            registry.registerBeanDefinition("cat", dogDefinition);
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Stream.of(beanFactory.getBeanDefinitionNames()).forEach(b -> {
                    BeanDefinition beanDefinition = beanFactory.getBeanDefinition(b);
                    // 对Dog Bean进行定义修改
                    if (!StringUtils.isEmpty(beanDefinition.getBeanClassName())) {
                        if (ClassUtils.resolveClassName(beanDefinition.getBeanClassName(), this.getClass().getClassLoader()).equals(Cat.class)) {
                            beanDefinition.getPropertyValues().add("name", "miao");
                        }
                    }
                }
        );
    }
}
