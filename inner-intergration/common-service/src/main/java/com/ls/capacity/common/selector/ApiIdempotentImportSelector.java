package com.ls.capacity.common.selector;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class ApiIdempotentImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{"com.ls.capacity.common.config.ApiIdempotentConfig"};
    }
}
