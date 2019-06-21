package com.example.springbootdemo.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;

import com.example.springbootdemo.constants.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author Ding RD
 * @since 2019-06-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_base_user")
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId("id")
    private String id;

    /**
     * 姓名
     */
    @TableField("name")
    private String name;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 性别 {男:1,女:2}
     */
    @TableField("sex")
    private String sex;

    /**
     * 年龄
     */
    @TableField("age")
    private Integer age;

    /**
     * 生日
     */
    @TableField("birthday")
    @DateTimeFormat(pattern = Constants.JAVA_DATE)
    @JsonFormat(pattern = Constants.JAVA_DATE, timezone = Constants.DEFAULT_TIME_ZONE)
    private LocalDate birthday;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 个人简介
     */
    @TableField("content")
    private String content;

    /**
     * 创建时间
     * DatetimeFormat将String转换成Date，一般前台给后台传值时用
     * JsonFormat(pattern="yyyy-MM-dd") 将Date转换成String  一般后台传值给前台时
     */
    @TableField("create_time")
    @DateTimeFormat(pattern = Constants.JAVA_DATETIME)
    @JsonFormat(pattern = Constants.JAVA_DATETIME, timezone = Constants.DEFAULT_TIME_ZONE)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField("update_time")
    @DateTimeFormat(pattern = Constants.JAVA_DATETIME)
    @JsonFormat(pattern = Constants.JAVA_DATETIME, timezone = Constants.DEFAULT_TIME_ZONE)
    private LocalDateTime updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
