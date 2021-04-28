package org.springframework.chapter12;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author xiaokexiang
 * @since 2021/4/28
 */
@Configuration
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
public class AopConfiguration {
}
