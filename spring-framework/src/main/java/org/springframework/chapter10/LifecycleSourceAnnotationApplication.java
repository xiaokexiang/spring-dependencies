package org.springframework.chapter10;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author xiaokexiang
 * @since 2021/4/25
 */
public class LifecycleSourceAnnotationApplication {
    public static void main(String[] args) {
        // 初始化注解上下文（初始化注解Bean定义解析器和类路径Bean的扫描器，两者功能类似，前者可以替代后者）
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(LifecycleSourceConfiguration.class);
        ctx.register(LifecycleNameReadPostProcessor.class);
        ctx.register(LifecycleDestructionPostProcessor.class);
        ctx.addApplicationListener((ApplicationListener<PayloadApplicationEvent<Integer>>) System.out::println);
        ctx.addBeanFactoryPostProcessor(new LifecycleBeanFactoryPostProcessor()); // 注入BeanFactoryPostProcessor
        System.out.println("================准备刷新IOC容器==================");

        ctx.refresh();

        System.out.println("================IOC容器刷新完毕==================");

        ctx.start();

        System.out.println("================IOC容器启动完成==================");

        Person person = ctx.getBean(Person.class);
        System.out.println(person);
        Cat cat = ctx.getBean(Cat.class);
        System.out.println(cat);

        System.out.println("================准备停止IOC容器==================");

        ctx.stop();

        System.out.println("================IOC容器停止成功==================");

        ctx.close();
    }

}
