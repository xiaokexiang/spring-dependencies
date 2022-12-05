package org.springframework.chapter23;

/**
 * @author xiaokexiang
 * @since 2022/12/5
 */
public interface Filter {
    void filter(Object request, Object response, FilterChain chain);
}
