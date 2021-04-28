package org.springframework.chapter11;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author xiaokexiang
 * @since 2021/4/27
 */
public class JdkProxy {

    public static void main(String[] args) {
        Partner partner = new PartnerImpl();
        Partner p = (Partner) Proxy.newProxyInstance(partner.getClass().getClassLoader(),
                partner.getClass().getInterfaces(),
                (proxy, method, args1) -> {
                    System.out.println("proxy ...");
                    return method.invoke(partner, args1);
                }
        );
        p.receiveMoney(100);
        p.playWith("lucy");
    }
}
