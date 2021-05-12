package org.springframework.chapter15;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author xiaokexiang
 * @since 2021/5/12
 */
@EnableAspectJAutoProxy
@ComponentScan("org.springframework.chapter15")
public class AopByJdkConfiguration {
}
