package org.springframework.chapter13;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author xiaokexiang
 * @since 2021/4/29
 */
@Configuration
@EnableAspectJAutoProxy
@ComponentScan("org.springframework.chapter13")
public class AopConfiguration {
}
