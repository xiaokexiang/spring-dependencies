package org.springframework.chapter4;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author xiaokexiang
 * @since 2021/4/16
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({Boss.class, BossImportSelector.class, BossBeanDefinition.class})
public @interface EnableLinked {
}
