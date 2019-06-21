package com.example.springbootdemo.core.aop;

import com.example.springbootdemo.constants.DataSourceEnum;
import com.example.springbootdemo.core.datasource.DataSourceContextHolder;
import com.example.springbootdemo.utils.annotation.DataSource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 使用AOP拦截特定的注解去动态的切换数据源
 *
 * @author Ding RD
 * @date 2019/6/20
 */
@Component
@Aspect
@Order(1)
public class DataSourceAspect {

    private Logger log = LoggerFactory.getLogger(DataSourceAspect.class);

    /**
     * within在类上设置
     * annotation在方法上进行设置
     */
    @Pointcut("@annotation(com.example.springbootdemo.utils.annotation.DataSource)")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void doBefore(JoinPoint joinPoint) {
//        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
//        DataSource datasource = method.getAnnotation(DataSource.class);
//        if (datasource == null) {
//            // 获取类上面的注解
//            datasource = joinPoint.getTarget().getClass().getAnnotation(DataSource.class);
//        }
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature;
        if (!(signature instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        methodSignature = (MethodSignature) signature;

        Object target = joinPoint.getTarget();
        Method currentMethod;
        try {
            currentMethod = target.getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
            DataSource datasource = currentMethod.getAnnotation(DataSource.class);
            // 获取注解上的数据源的值的信息
            if (datasource != null) {
                DataSourceContextHolder.setDataSource(datasource.value().id());
                log.info("设置数据源为：{}", datasource.value().desc());
            } else {
                DataSourceContextHolder.setDataSource(DataSourceEnum.DB1.id());
                log.info("设置数据源为：{}", DataSourceEnum.DB1.desc());
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @After("pointcut()")
    public void doAfter() {
        // 清理掉当前设置的数据源，让默认的数据源不受影响
        log.info("清空数据源信息！");
        DataSourceContextHolder.clear();
    }
}
