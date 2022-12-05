package org.springframework.chapter23;

import org.springframework.stereotype.Component;

/**
 * @author xiaokexiang
 * @since 2022/12/5
 */
@Component
public class FilterTwo implements Filter {

    @Override
    public void filter(Object request, Object response, FilterChain chain) {
        System.out.println("before filter two");
        chain.doFilter(request, response);
        System.out.println("after filter two");
    }
}
