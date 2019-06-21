package com.example.springbootdemo.constants;

/**
 * 数据源枚举
 *
 * @author Ding RD
 * @date 2019/6/20
 */
public enum DataSourceEnum {

    DB1("db1", "主库"), DB2("db2", "副库");

    private String id;
    private String desc;

    DataSourceEnum(String id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public String id() {
        return id;
    }

    public String desc() {
        return desc;
    }

    @Override
    public String toString() {
        return "{" + id() + "=" + desc() + "}";
    }

    //获取指定ID的枚举描述
    public static String getDesc(String id) {
        //所有的枚举值
        DataSourceEnum[] values = values();
        //遍历查找
        for (DataSourceEnum t : values) {
            if (t.id().equals(id)) {
                return t.desc();
            }
        }
        return "";
    }

    //是否包含枚举代码
    public static boolean isExist(String id) {
        //所有的枚举值
        DataSourceEnum[] values = values();
        //遍历查找
        for (DataSourceEnum t : values) {
            if (t.id().equals(id)) {
                return true;
            }
        }
        return false;
    }
}
