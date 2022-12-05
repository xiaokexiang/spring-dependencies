package org.springframework.chapter23;

/**
 * @author xiaokexiang
 * @since 2022/12/5
 */
public interface FilterChain {
    void doFilter(Object request, Object response);
}
