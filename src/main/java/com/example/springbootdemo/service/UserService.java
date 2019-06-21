package com.example.springbootdemo.service;

import com.example.springbootdemo.constants.DataSourceEnum;
import com.example.springbootdemo.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.springbootdemo.utils.annotation.DataSource;

import java.util.List;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author Ding RD
 * @since 2019-06-20
 */
public interface UserService extends IService<User> {

    List getList();
}
