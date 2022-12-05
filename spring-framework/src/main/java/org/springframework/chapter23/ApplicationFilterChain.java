package org.springframework.chapter23;

/**
 * @author xiaokexiang
 * @since 2022/12/5
 */
public class ApplicationFilterChain implements FilterChain {

    private Filter[] filters;
    private int n;
    private int pos = 0;

    public ApplicationFilterChain(Filter[] filters, int n) {
        this.filters = filters;
        this.n = n;
    }

    @Override
    public void doFilter(Object request, Object response) {
        if (pos < n) {
            filters[pos++].filter(request, response, this);
        }
    }
}
