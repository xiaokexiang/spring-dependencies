package org.springframework.chapter6;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author xiaokexiang
 * @since 2021/4/19
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(PersonBeanDefinitionRegistry.class)
public @interface EnablePerson {
}
