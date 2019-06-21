package com.example.springbootdemo.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.example.springbootdemo.config.properties.BaseDataSourceProperties;
import com.example.springbootdemo.config.properties.Db1DataSourceProperties;
import com.example.springbootdemo.config.properties.Db2SourceProperties;
import com.example.springbootdemo.constants.DataSourceEnum;
import com.example.springbootdemo.core.datasource.MultipleDataSource;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.sql.SQLException;
import java.util.HashMap;

/**
 * MybatisPlus配置类
 *
 * @author Ding RD
 * @date 2019/6/20
 *
 * 切换数据源和开启事务管理是分先后的，spring事务管理是跟数据库事务绑定一起的，
 * 开启一个事务就已经和数据源绑定在一起，再切换数据源时，会造成切换数据源失效。
 * 所以切换数据源aop要在开启事务aop之前，切换数据源aop的order是1，事务的是2。
 */
@Configuration
@EnableTransactionManagement(order = 2)
@MapperScan(basePackages = {"com.example.springbootdemo.mapper"})
public class MybatisPlusConfig {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private BaseDataSourceProperties baseDataSourceProperties;

    private Db1DataSourceProperties db1DataSourceProperties;

    private Db2SourceProperties db2SourceProperties;

    @Autowired
    public void setBaseDataSourceProperties(BaseDataSourceProperties baseDataSourceProperties) {
        this.baseDataSourceProperties = baseDataSourceProperties;
    }

    @Autowired
    public void setDb1DataSourceProperties(Db1DataSourceProperties db1DataSourceProperties) {
        this.db1DataSourceProperties = db1DataSourceProperties;
    }

    @Autowired
    public void setDb2SourceProperties(Db2SourceProperties db2SourceProperties) {
        this.db2SourceProperties = db2SourceProperties;
    }

    /**
     * 数据源一
     */
    private DruidDataSource db1DataSource() throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        baseDataSourceProperties.config(dataSource);
        db1DataSourceProperties.config(dataSource);
        return dataSource;
    }

    /**
     * 数据源二
     */
    private DruidDataSource db2DataSource() throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        baseDataSourceProperties.config(dataSource);
        db2SourceProperties.config(dataSource);
        return dataSource;
    }

    /**
     * 多数据源连接池配置
     */
    @Bean
    public MultipleDataSource multipleDataSource() {
        try {
            MultipleDataSource multipleDataSource = new MultipleDataSource();
            HashMap<Object, Object> hashMap = new HashMap<>(2);
            DruidDataSource db1DataSource = db1DataSource();
            db1DataSource.init();
            logger.info("数据库一初始化完成！");
            DruidDataSource db2DataSource = db2DataSource();
            db2DataSource.init();
            logger.info("系统管理中心数据库初始化完成！");
            hashMap.put(DataSourceEnum.DB1.id(), db1DataSource);
            hashMap.put(DataSourceEnum.DB2.id(), db2DataSource);
            multipleDataSource.setTargetDataSources(hashMap);
            multipleDataSource.setDefaultTargetDataSource(db1DataSource);
            return multipleDataSource;
        } catch (SQLException ex) {
            logger.error("加载数据源过程中出现异常：", ex);
            throw new RuntimeException(ex);
        }
    }
}
