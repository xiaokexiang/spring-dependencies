package org.springframework.chapter13;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.UndeclaredThrowableException;
import java.sql.Connection;

/**
 * @author xiaokexiang
 * @since 2021/4/29
 */
@Aspect
@Component
public class TransactionAspect {

    // 用于实现引介通知如果value是接口，那么最后加上+即可
    //
    @DeclareParents(value = "org.springframework.chapter13.FinanceService",
            defaultImpl = MoneyValidatorImpl.class)
    private MoneyValidator moneyValidator;

    @Pointcut("@annotation(org.springframework.chapter13.Transactional)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Connection connection = JdbcUtils.getConnection();
        connection.setAutoCommit(false);
        try {
            int money = (int) pjp.getArgs()[2];
            MoneyValidator moneyValidator = (MoneyValidator) pjp.getThis();
            if (moneyValidator.validate(money)) {
                System.out.println("校验金额 。。。 ");
            } else {
                throw new IllegalArgumentException("转账金额不合法！");
            }
            Object proceed = pjp.proceed();
            connection.commit();
            return proceed;
        } catch (UndeclaredThrowableException t) {
            connection.rollback();
            throw t;
        } finally {
            JdbcUtils.remove();
        }
    }
}
