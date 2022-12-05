package org.springframework.chapter22;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Map;

/**
 * @author xiaokexiang
 * @since 2022/11/29
 */
@Component
@EnableTransactionManagement
@EnableAspectJAutoProxy
public class HelloService {

    public void say(String hello) {
        System.out.println("hello");
    }

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("org.springframework.chapter22");
        HelloService helloService = context.getBean("helloService", HelloService.class);
        TransactionTemplate template = context.getBean(TransactionTemplate.class);
        template.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {

            }
        });
        EnableAspectJAutoProxy annotation = AnnotationUtils.findAnnotation(HelloService.class, EnableAspectJAutoProxy.class);
        Map<String, Object> annotationAttributes = AnnotationUtils.getAnnotationAttributes(annotation);
        helloService.say("hello1");
    }
}
