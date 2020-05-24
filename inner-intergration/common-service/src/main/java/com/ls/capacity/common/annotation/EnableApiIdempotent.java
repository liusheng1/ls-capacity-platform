package com.ls.capacity.common.annotation;


import com.ls.capacity.common.selector.ApiIdempotentImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启动幂等拦截器
 * 自动装配starter
 * 选择器
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ApiIdempotentImportSelector.class)
public @interface EnableApiIdempotent {
}
