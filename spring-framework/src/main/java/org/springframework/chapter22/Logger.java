package org.springframework.chapter22;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author xiaokexiang
 * @since 2022/11/29
 */
@Aspect
@Component
public class Logger {

    @Before("pointCut()")
    public void beforePrint(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String name = joinPoint.getSignature().getName();
        System.out.println("Logger beforePrint run ......");
    }

    @Around("pointCut()")
    public Object aroundPrint(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String name = proceedingJoinPoint.getSignature().getName();
        System.out.println("around beforePrint run ......");
        Object proceed = proceedingJoinPoint.proceed();
        System.out.println("around afterPrint run ......");
        return proceed;
    }

    @Pointcut("execution(public * org.springframework.chapter22.HelloService.*(..))")
    public void pointCut() {
    }
}