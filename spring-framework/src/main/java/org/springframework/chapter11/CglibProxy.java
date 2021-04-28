package org.springframework.chapter11;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author xiaokexiang
 * @since 2021/4/27
 */
public class CglibProxy {
    public static void main(String[] args) {
        Partner partner = new PartnerImpl();
        Partner p = (Partner)Enhancer.create(partner.getClass(),
                (MethodInterceptor) (o, method, objects, methodProxy) -> {
                    System.out.println("cglib proxy ...");
                    return method.invoke(partner, objects);
                });
        p.receiveMoney(200);
        p.playWith("jack");
    }
}
