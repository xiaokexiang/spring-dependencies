package org.springframework.chapter18;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author xiaokexiang
 * @since 2021/5/14
 */
public class TransactionSourceTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TransactionalConfiguration.class);
        FinanceService bean = context.getBean(FinanceService.class);
        bean.transfer(1L, 2L, 1000);
    }
}
