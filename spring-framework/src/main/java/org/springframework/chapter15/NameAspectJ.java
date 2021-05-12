package org.springframework.chapter15;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author xiaokexiang
 * @since 2021/5/12
 * 千万不要使用idea的新增aspect类，他是aj后缀不是class
 */
@Aspect
@Component
public class NameAspectJ {

    @Pointcut("execution(* org.springframework.chapter15.NameServiceImpl.say())")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void beforePrint() {
        System.out.println("prepare to say ... ");
    }
}
