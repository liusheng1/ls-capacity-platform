package com.ls.capacity.db.annotation;

import java.lang.annotation.*;

/** 数据源选择 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
  //  数据源名称
  String name();
}
