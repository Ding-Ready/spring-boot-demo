-- ----------------------------
-- 在Mysql中取消外键约束,如果表和表之间建立的外键约束，则无法删除表及修改表结构。
-- ----------------------------
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for demo
-- ----------------------------
DROP TABLE IF EXISTS `t_base_user`;
CREATE TABLE `t_base_user`
(
    `id`           varchar(50)    NOT NULL COMMENT '主键ID',
    `name`         varchar(30)    default NULL COMMENT '姓名',
    `password`     varchar(20)    default NULL COMMENT '密码',
    `sex`          varchar(2)     default NULL COMMENT '性别 {男:1,女:2}',
    `age`          int(11)        default NULL COMMENT '年龄',
    `birthday`     date           default NULL COMMENT '生日',
    `email`        varchar(50)    default NULL COMMENT '邮箱',
    `content`      varchar(1000)  default NULL COMMENT '个人简介',
    `create_time`  datetime       default NULL COMMENT '创建时间',
    `update_time`  datetime       default NULL COMMENT '修改时间',
    PRIMARY KEY (`id`)
) COMMENT = '用户信息表' ENGINE = InnoDB DEFAULT CHARSET = utf8;

-- ----------------------------
-- Records of demo
-- ----------------------------
INSERT INTO `t_base_user` VALUES ('08375a2dff80e821d5a158dd98302b23', 'admin', '123456', 1, null, null, null, null, '2019-06-18 15:10:10', null);

SET FOREIGN_KEY_CHECKS = 1;