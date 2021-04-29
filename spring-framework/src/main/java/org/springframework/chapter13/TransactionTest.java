package org.springframework.chapter13;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author xiaokexiang
 * @since 2021/4/29
 */
public class TransactionTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
                AopConfiguration.class);
        FinanceService financeService = ctx.getBean(FinanceService.class);
        financeService.transfer(1L, 2L, 100);
    }
}
