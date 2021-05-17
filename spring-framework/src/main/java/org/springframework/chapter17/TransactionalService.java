package org.springframework.chapter17;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;

/**
 * @author xiaokexiang
 * @since 2021/5/13
 * 外 REQUIRED 内 REQUIRES_NEW   内报错影响外 外报错影响内
 */
@Service
public class TransactionalService implements ApplicationContextAware {


    @Resource
    private TransactionalDao transactionalDao;
    @Resource
    private Step2Service step2Service;

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
    public void step1() {
        System.out.println("step1 begin ...");
        System.out.println(TransactionSynchronizationManager.getCurrentTransactionName());
        transactionalDao.insert(900, 1);
//        try {
//            step2Service.step2();
//            int i = 1 / 0;
//        } catch (Exception e) {
//            System.out.println("step1 ... error");
//        }

        applicationContext.publishEvent(new Account());
        System.out.println("step1 end ...");
    }

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @TransactionalEventListener // 事务监听器，需要手动发布事件
    public void onSave(PayloadApplicationEvent<Account> event) {
        System.out.println("监听到保存用户事务提交成功 ......");
    }

    static class Account {
    }
}
