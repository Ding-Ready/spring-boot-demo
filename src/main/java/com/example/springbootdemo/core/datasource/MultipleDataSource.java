package com.example.springbootdemo.core.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 多数据源的选择
 *
 * @author Ding RD
 * @date 2019/6/20
 */
public class MultipleDataSource extends AbstractRoutingDataSource {

    /**
     * 根据Key获取数据源的信息
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDataSource();
    }
}
