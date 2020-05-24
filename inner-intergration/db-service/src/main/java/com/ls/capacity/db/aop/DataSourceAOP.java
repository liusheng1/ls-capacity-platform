package com.ls.capacity.db.aop;

import com.ls.capacity.db.annotation.DataSource;
import com.ls.capacity.db.constant.DataSourceKey;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;



/**
 * 切换数据源Advice
 *
 */
@Slf4j
@Aspect
@Order(-1) //保证该AOP 在@Transaction 之前执行
public class DataSourceAOP {
    @Before("@annatation(ds)")
    public void changeDataSource(JoinPoint point, DataSource ds){
        //获取数据源名称
        String dsId = ds.name();
        DataSourceKey.valueOf(dsId)


    }
}
