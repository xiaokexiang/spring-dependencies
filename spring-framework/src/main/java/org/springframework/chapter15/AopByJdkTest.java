package org.springframework.chapter15;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author xiaokexiang
 * @since 2021/5/12
 * Spring采用JDK动态代理
 */
public class AopByJdkTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AopByJdkConfiguration.class);
        NameService bean = context.getBean(NameService.class);
        bean.say();
        System.out.println(context.getBean(AopByJdkConfiguration.class) == context.getBean(AopByJdkConfiguration.class));
    }
}
