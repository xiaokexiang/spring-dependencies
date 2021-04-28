package org.springframework.chapter12;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author xiaokexiang
 * @since 2021/4/28
 */
@Aspect
@Component
public class Logger {

    @Pointcut("execution(* org.springframework.chapter12.DoServiceImpl.doSomething())")
    public void pointcut() {
    }

    @Pointcut("@annotation(org.springframework.chapter12.Log)")
    public void annotationPointcut() {
    }

    @Before("pointcut()")
    public void beforePrint() {
        System.out.println("Logger before run .....");
    }

    @AfterReturning(value = "pointcut()", returning = "obj")
    public void afterReturningPrint(Object obj) {
        System.out.println("Logger afterReturningPrint run ......");
        System.out.println(obj);
    }

    @AfterThrowing(value = "pointcut()", throwing = "r")
    public void afterThrowingPrint(RuntimeException r) {
        System.out.println("Logger afterThrowingPrint run ......");
        System.out.println(r.getMessage());
    }

    @After("pointcut()")
    public void afterPrint() {
        System.out.println("Logger afterPrint run ......");
    }

    // 整体的执行顺序先于其他模式
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("around before ...");
        try {
            System.out.println(pjp.getTarget()); // 返回被代理对象
            System.out.println(pjp.getThis()); // 返回代理对象
            System.out.println(Arrays.toString(pjp.getArgs())); // 返回被拦截方法的参数
            MethodSignature signature = (MethodSignature) pjp.getSignature();
            System.out.println(signature); // 返回被拦截方法签名参数
            System.out.println(signature.getMethod()); // 返回被拦截方法相关参数
            return pjp.proceed();
        } catch (Throwable t) {
            System.out.println("around throw ...");
            throw t;
        } finally {
            System.out.println("around after ...");
        }
    }

    @Before("annotationPointcut()")
    public void beforePrint1() {
        System.out.println("Logger before run .....");
    }

    @AfterReturning("annotationPointcut()")
    public void afterReturningPrint1() {
        System.out.println("Logger afterReturningPrint run ......");
    }

    @AfterThrowing("annotationPointcut()")
    public void afterThrowingPrint1() {
        System.out.println("Logger afterThrowingPrint run ......");
    }

    @After("annotationPointcut()")
    public void afterPrint1() {
        System.out.println("Logger afterPrint run ......");
    }

    // 整体的执行顺序先于其他模式
    @Around("annotationPointcut()")
    public Object around1(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("around before ...");
        try {
            return pjp.proceed();
        } catch (Throwable t) {
            System.out.println("around throw ...");
            throw t;
        } finally {
            System.out.println("around after ...");
        }
    }
}
