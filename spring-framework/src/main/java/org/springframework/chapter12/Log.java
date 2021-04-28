package org.springframework.chapter12;

import java.lang.annotation.*;

/**
 * @author xiaokexiang
 * @since 2021/4/28
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Log {
}
