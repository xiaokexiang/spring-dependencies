package org.springframework.chapter14;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @author xiaokexiang
 * @since 2021/4/29
 */
@Component
@Aspect
public class LogAspect {

    @Before("execution(* org.springframework.chapter14.UserService.get(*))")
    public void beforePrint() {
        System.out.println("LogAspect beforePrint ......");
    }

}
