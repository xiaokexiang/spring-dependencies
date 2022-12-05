package org.springframework.chapter17;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;

/**
 * @author xiaokexiang
 * @since 2021/5/13
 * TransactionSynchronizationManager.getCurrentTransactionName() 获取当前事务的名称
 * TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 用于回滚事务且不向上抛出异常
 * <p>
 * 同service:
 * serviceA.a(无事务)   -> serviceA.b(有事务)  -> a或b报错都不会回滚
 * serviceA.a(Required) -> serviceA.b(无事务|Required|Requires_new|Nested) -> a或b报错都会回滚(同一个类的方法不存在事务传播属性)
 * <p>
 * serviceA.a(无事务) -> serviceB.b(有事务) -> a报错不会滚，b报错回滚
 * serviceA.a(Required) -> serviceB.b(无事务|Required|Nested) -> a或b报错都会回滚（一个事务）
 * serviceA.a(Required) -> serviceB.b(Requires_new) -> a报错不影响b提交,b报错影响a提交
 */
@Service
public class TransactionalService implements ApplicationContextAware {


    @Resource
    private TransactionalDao transactionalDao;
    @Resource
    private Step2Service step2Service;

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public void step1() {
        System.out.println("step1 begin ...");
        System.out.println(TransactionSynchronizationManager.getCurrentTransactionName());
        transactionalDao.insert(900, 1);
        // 同一个class内事务之间的调用
//        step3();
//        ((TransactionalService) (AopContext.currentProxy())).step3();
        // 不同类之间事务的互相调用
        System.out.println("step1 end ...");
        step2Service.step2();
        int i = 1 / 0;
        applicationContext.publishEvent(new Account());
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.NESTED)
    public void step3() {
        System.out.println("step3 begin ...");
        System.out.println(TransactionSynchronizationManager.getCurrentTransactionName());
        transactionalDao.insert(1100, 2);
//        int i = 1 / 0;
        System.out.println("step3 end ...");
    }

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT) // 事务监听器，需要手动发布事件
    public void beforeSave(PayloadApplicationEvent<Account> event) {
        System.out.println("监听到即将保存用户事务 ......");
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT) // 事务监听器，需要手动发布事件
    public void onSave(PayloadApplicationEvent<Account> event) {
        System.out.println("监听到保存用户事务提交成功 ......");
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION) // 事务监听器，需要手动发布事件
    public void afterSave(PayloadApplicationEvent<Account> event) {
        System.out.println("监听到保存用户事务提交完成 ......");
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK) // 事务监听器，需要手动发布事件
    public void afterRollback(PayloadApplicationEvent<Account> event) {
        System.out.println("监听到保存用户事务即将回滚 ......");
    }

    static class Account {
    }
}
