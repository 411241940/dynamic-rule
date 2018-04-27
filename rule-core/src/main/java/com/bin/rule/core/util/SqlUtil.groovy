package com.bin.rule.core.util

/**
 * sql工具类
 * @author bin
 * @version 1.0 2018/4/27
 * */
class SqlUtil {

    static String buildByDriverClassName(String driverClassName) {
        String dbType = null
        if (driverClassName.contains("mysql")) {
            dbType = "mysql"
        } else if (driverClassName.contains("sqlserver")) {
            dbType = "sqlserver"
        } else if (driverClassName.contains("oracle")) {
            dbType = "oracle"
        }
        return dbType
    }

    static String buildCreateTableSql(String driverClassName, String tableName) {

        StringBuilder createTableSql = new StringBuilder()
        String dbType = buildByDriverClassName(driverClassName)
        switch (dbType) {
            case "mysql":
                createTableSql << """CREATE TABLE ${tableName} (
                                        `id` varchar(64) NOT NULL,
                                        `name` varchar(256) ,
                                        `code` longblob,
                                        PRIMARY KEY (`id`)
                                     )
                                  """
                break

            case "oracle":
                createTableSql << """CREATE TABLE ${tableName} (
                                        `id` varchar2(64) NOT NULL,
                                        `name` varchar2(256) ,
                                        `code` BLOB,
                                        PRIMARY KEY (`id`)
                                     )
                                  """
                break

            default: throw new RuntimeException("dbType类型不支持,目前仅支持mysql oracle.")
        }

        return createTableSql.toString()
    }

}
