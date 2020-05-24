package com.ls.capacity.db.util;

import com.ls.capacity.db.constant.DataSourceKey;

/** 用于数据源的贴换 */
public class DataSourceHolder {
  // 1.注意使用ThreadLocal，微服务下游建议使用信号量
  private static final ThreadLocal<DataSourceKey> dataSourceKey = new ThreadLocal<>();
  // 2.得到当前的数据库连接
  public static DataSourceKey getDataSourceKey() {
    return dataSourceKey.get();
  }
  // 3.设置当前的数据库连接
  public static void setDataSourceKey(DataSourceKey key) {
    dataSourceKey.set(key);
  }
  // 4.清除当前的数据库连接
  public static void clearDataSourceKey() {
    dataSourceKey.remove();
  }
}
