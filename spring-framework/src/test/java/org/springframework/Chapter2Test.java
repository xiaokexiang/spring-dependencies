package org.springframework;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.chapter2.BeanLifeCycleConfiguration;
import org.springframework.chapter2.BeanScope;
import org.springframework.chapter2.PersonFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author xiaokexiang
 * @since 2021/4/12
 */
public class Chapter2Test {

    @Test
    public void factoryBean() throws Exception {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext("org.springframework.chapter2");
        PersonFactoryBean bean = applicationContext.getBean(PersonFactoryBean.class);
        PersonFactoryBean bean2 = (PersonFactoryBean) applicationContext.getBean(BeanFactory.FACTORY_BEAN_PREFIX + "personFactoryBean");
        System.out.println(bean.getObject());
        System.out.println(bean2.getObject());
    }

    @Test
    public void beanScope() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext("org.springframework.chapter2");
        System.out.println(
                applicationContext.getBean(BeanScope.class) == applicationContext.getBean(BeanScope.class));
    }

    @Test
    public void testLifeCycle() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext("org.springframework.chapter2");
        BeanLifeCycleConfiguration bean = applicationContext.getBean(BeanLifeCycleConfiguration.class);
        System.out.println(bean);
        applicationContext.close();
    }
}
