package org.springframework.chapter23;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author xiaokexiang
 * @since 2022/12/5
 * 参考servlet filter模型实现过滤器功能
 */
public class FilterTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("org.springframework.chapter23");
        Filter[] filters = context.getBeansOfType(Filter.class).values().toArray(new Filter[]{});
        FilterChain filterChain = new ApplicationFilterChain(filters, filters.length);
        filterChain.doFilter("request", "response");
    }
}
