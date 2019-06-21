package com.example.springbootdemo.config.properties;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.util.JdbcUtils;
import com.alibaba.druid.wall.WallFilter;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 基础数据源配置
 *
 * @author Ding RD
 * @date 2019/6/20
 */
@Data
@Component
public class BaseDataSourceProperties {

    private String driverClassName = "";

    private Integer initialSize = 2;

    private Integer minIdle = 1;

    private Integer maxActive = 20;

    private Integer maxWait = 60000;

    private Integer timeBetweenEvictionRunsMillis = 60000;

    private Integer minEvictableIdleTimeMillis = 300000;

    private String validationQuery = "SELECT 'x'";

    private Boolean testWhileIdle = true;

    private Boolean testOnBorrow = false;

    private Boolean testOnReturn = false;

    private Boolean poolPreparedStatements = true;

    private Integer maxPoolPreparedStatementPerConnectionSize = 20;

    private String filters = "stat";

    public void config(DruidDataSource dataSource) {
        // 定义初始连接数
        dataSource.setInitialSize(initialSize);
        // 最小空闲
        dataSource.setMinIdle(minIdle);
        // 定义最大连接数
        dataSource.setMaxActive(maxActive);
        // 最长等待时间
        dataSource.setMaxWait(maxWait);

        // 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);

        // 配置一个连接在池中最小生存的时间，单位是毫秒
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        // 验证数据库连接
        dataSource.setValidationQuery(validationQuery);
        // 申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
        dataSource.setTestWhileIdle(testWhileIdle);
        // 申请连接时执行validationQuery检测连接是否有效
        dataSource.setTestOnBorrow(testOnBorrow);
        // 归还连接时执行validationQuery检测连接是否有效
        dataSource.setTestOnReturn(testOnReturn);

        // 打开PSCache，并且指定每个连接上PSCache的大小
        dataSource.setPoolPreparedStatements(poolPreparedStatements);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        try {
            // 通过别名的方式配置扩展插件，常用的插件有：
            // 监控统计用的filter:stat日志用的filter:log4j防御sql注入的filter:wall
            dataSource.setFilters(filters);
            // 同时配置了filters和proxyFilters，是组合关系，并非替换关系
            dataSource.setProxyFilters(customFilter());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 自定义过滤器设置
     *
     * @return 自定义过滤器列表
     */
    private List<Filter> customFilter() {
        List<Filter> filters = new ArrayList<>();
        filters.add(slf4jLogFilter());
        filters.add(statFilter());
        filters.add(wallFilter());
        return filters;
    }

    /**
     * 日志记录过滤器
     *
     * @return Slf4jLogFilter
     */
    private Slf4jLogFilter slf4jLogFilter() {
        Slf4jLogFilter slf4jLogFilter = new Slf4jLogFilter();
        //禁用数据源日志
        slf4jLogFilter.setDataSourceLogEnabled(false);
        //禁用连接日志
        slf4jLogFilter.setConnectionLogEnabled(false);
        //禁用预执行语句日志
        slf4jLogFilter.setStatementLogEnabled(false);
        //禁用结果集日志
        slf4jLogFilter.setResultSetLogEnabled(false);
        //输出可执行的SQL
        slf4jLogFilter.setStatementExecutableSqlLogEnable(true);
        return slf4jLogFilter;
    }

    /**
     * 统计过滤器设置
     *
     * @return StatFilter
     */
    private StatFilter statFilter() {
        StatFilter statFilter = new StatFilter();
        //SQL合并配置
        statFilter.setMergeSql(true);
        //启用慢SQL记录功能
        statFilter.setLogSlowSql(true);
        //慢SQL时间（10秒）
        statFilter.setSlowSqlMillis(10000);
        return statFilter;
    }

    /**
     * SQL防御
     *
     * @return WallFilter
     */
    private WallFilter wallFilter() {
        WallFilter wallFilter = new WallFilter();
        wallFilter.setDbType(JdbcUtils.MYSQL);
        return wallFilter;
    }
}
