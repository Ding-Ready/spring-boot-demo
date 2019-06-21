package com.example.springbootdemo.core.datasource;

/**
 * 根据当前线程来选择具体的数据源
 *
 * @author Ding RD
 * @date 2019/6/20
 */
public class DataSourceContextHolder {

    private static final ThreadLocal<String> contextHolder = new InheritableThreadLocal<>();

    /**
     * 提供给AOP去设置当前的线程的数据源的信息
     *
     * @param dataSource 数据源名称
     */
    public static void setDataSource(String dataSource) {
        contextHolder.set(dataSource);
    }

    /**
     * 提供给AbstractRoutingDataSource的实现类，通过key选择数据源
     */
    public static String getDataSource() {
        return contextHolder.get();
    }

    /**
     * 清除上下文数据，使用默认的数据源
     */
    public static void clear() {
        contextHolder.remove();
    }
}
