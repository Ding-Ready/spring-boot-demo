package com.example.springbootdemo.config.properties;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

/**
 * 数据源一配置
 *
 * @author Ding RD
 * @date 2019/6/20
 */
@Data
@Component
@ConfigurationProperties(prefix = Db1DataSourceProperties.PREFIX)
public class Db1DataSourceProperties {

    public static final String PREFIX = "spring.datasource.db1";

    private String url = "";

    private String username = "";

    private String password = "";

    /**
     * 默认只启用SQL防火墙过滤器
     */
    private String filters = "wall";

    public void config(DruidDataSource dataSource) throws SQLException {
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setFilters(filters);
    }
}
