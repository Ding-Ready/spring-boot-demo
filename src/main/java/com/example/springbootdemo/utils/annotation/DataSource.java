package com.example.springbootdemo.utils.annotation;

import com.example.springbootdemo.constants.DataSourceEnum;

import java.lang.annotation.*;

/**
 * 多数据源标识
 *
 * @author Ding RD
 * @date 2019/6/20
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {

    DataSourceEnum value() default DataSourceEnum.DB1;
}
