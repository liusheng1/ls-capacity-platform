package com.ls.capacity.db.util;


import com.ls.capacity.db.constant.DataSourceKey;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * spring动态数据源（需要继承AbstractRoutingDataSource）
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    private Map<Object,Object> datasource;
    public DynamicDataSource (){
        datasource=new HashMap<>();
        super.setTargetDataSources(datasource);
    }

    public <T extends DataSource> void addDataSource(DataSourceKey key, T data){
        datasource.put(key,data);
    }
    @Override
    protected Object determineCurrentLookupKey() {

        return DataSourceHolder.getDataSourceKey();
    }
}
