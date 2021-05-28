package org.springframework.chapter19;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.stream.Stream;

/**
 * @author xiaokexiang
 * @since 2021/5/28
 * spring 循环依赖 三级缓存机制
 */
public class SpringCircularDependencyTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("org.springframework.chapter19");
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        Stream.of(beanDefinitionNames).forEach(System.out::println);
        // 流程简述： cat -> doCreateBean() -> cat 加入到 正在创建缓存和三级缓存 -> resolveDependency
        // -> person -> doCreateBean() -> person 加入到 正在创建缓存和三级缓存 -> resolveDependency
        // -> cat -> doCreateBean() -> cat 从三级缓存移到二级缓存 -> person 从三级缓存和正在创建缓存中移除 -> person 移到一级缓存
        // -> cat 从三级缓存和正在创建缓存中移除 -> cat 移到一级缓存

        // 需要三级缓存的原因？
        // bean在实例化后就存在引用了，不把这个引用暴露出去的原因在于AOP，不能把未增强的bean返回出去，第三级缓存基于ObjectFactory，
        // 当它感知到有别的bean加载这个依赖的时候会调用AOP的BeanPostProcessor初始化后置处理器进行增强，保证返回的是增强过的对象。
    }
}
