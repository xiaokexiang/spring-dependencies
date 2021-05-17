package org.springframework.chapter17;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;

/**
 * @author xiaokexiang
 * @since 2021/5/13
 */
@Service
public class Step2Service {
    @Resource
    private TransactionalDao transactionalDao;

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
    public void step2() {
        try {
            System.out.println("step2 begin ...");
            System.out.println(TransactionSynchronizationManager.getCurrentTransactionName());
            transactionalDao.insert(1100, 2);
            int i = 1 / 0;
            System.out.println("step2 end ...");
        } catch (Exception e) {
            System.out.println("12121");

        }

    }
}
