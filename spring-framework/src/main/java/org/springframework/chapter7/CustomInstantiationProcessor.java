package org.springframework.chapter7;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author xiaokexiang
 * @since 2021/4/20
 */
@Component
public class CustomInstantiationProcessor implements InstantiationAwareBeanPostProcessor {

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
//        if (beanName.equals("cat")) {
//            System.out.println();
//            // 如果返回不为null，那么下一步会执行beanPostProcessor的after逻辑
//            return new Cat();
//        }
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        return true;
    }

    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        if (beanName.equals("cat")) {
            MutablePropertyValues propertyValues = new MutablePropertyValues(pvs);
            propertyValues.addPropertyValue("id","12345");
            return propertyValues;
        }
        return null;
    }
}
