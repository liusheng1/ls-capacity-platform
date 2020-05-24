package com.ls.capacity.db;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.ls.capacity.db.aop.DataSourceAOP;
import com.ls.capacity.db.constant.DataSourceKey;
import com.ls.capacity.db.util.DynamicDataSource;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/** 在设置了spring.datasource.enable.dynamic 等于true是开启多数据源，配合日志 */
@Configuration
@Import(DataSourceAOP.class)
@AutoConfigureBefore(value = {DataSourceAutiConfig.class, MybatisAutoConfiguration.class})
@ConditionalOnProperty(
    name = {"spring.datasource.dynamic.enable"},
    matchIfMissing = false,
    havingValue = "true")
public class DataSourceAutiConfig {
  //	1.创建数据源
  //	所有引入db-core的模块都需要一个核心库，可以是user-center，也可以是oauth-center,file-center ,sms-center
  @Bean
  @ConfigurationProperties("spring.datasource.druid.core")
  public DataSource dataSourceCore() {
    return DruidDataSourceBuilder.create().build();
  }
  //    2.所有的核心库共享一个日志中心模块，改模块不采用mysql中的innodb引擎，采用归档引擎
  @Bean
  @ConfigurationProperties("spring.datasource.duid.log")
  public DataSource dataSourceLog() {
    return DruidDataSourceBuilder.create().build();
  }

  @Primary
  @Bean // 只需要纳入动态数据源到spring容器
  public DataSource dataSource() {
    DynamicDataSource dataSource = new DynamicDataSource();
    DataSource coreDataSource =  dataSourceCore() ;
    DataSource logDataSource =  dataSourceLog();
    dataSource.addDataSource(DataSourceKey.core, coreDataSource);
    dataSource.addDataSource(DataSourceKey.log, logDataSource);
    dataSource.setDefaultTargetDataSource(coreDataSource);
    return dataSource;
  }
  public DataSourceTransactionManager transactionManager(   @Qualifier("dataSource") DataSource dataSource){
    return new DataSourceTransactionManager(dataSource);
  }
}
