package org.springframework.chapter4;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author xiaokexiang
 * @since 2021/4/16
 */
public class BossImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{Nothing.class.getName()};
    }

    static class Nothing {

    }
}
