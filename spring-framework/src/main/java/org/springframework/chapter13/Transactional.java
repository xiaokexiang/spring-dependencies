package org.springframework.chapter13;

import java.lang.annotation.*;

/**
 * @author xiaokexiang
 * @since 2021/4/29
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD) //
public @interface Transactional {
}
