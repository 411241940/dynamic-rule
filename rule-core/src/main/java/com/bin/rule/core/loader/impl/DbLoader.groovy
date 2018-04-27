package com.bin.rule.core.loader.impl

import com.alibaba.druid.pool.DruidDataSource
import com.bin.rule.core.HandlerFactory
import com.bin.rule.core.config.DbConfig
import com.bin.rule.core.config.RuleConfig
import com.bin.rule.core.entity.Rule
import com.bin.rule.core.util.SqlUtil
import groovy.sql.GroovyRowResult
import groovy.sql.Sql
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * 数据库代码加载器
 *
 * @author bin
 * @version 1.0 2018/4/25
 * */
class DbLoader extends AbstractLoader {

    private DruidDataSource dataSource

    private String tableName

    private Logger logger = LoggerFactory.getLogger(DbLoader.class)

    @Override
    String load(String name) {
        String selectSql = "select code from ${tableName} where name = '${name}'"
        Sql sql = new Sql(dataSource)
        GroovyRowResult result = sql.firstRow(selectSql)
        byte[] bytes = result.code
        return serializer.deSerialize(bytes, String.class)
    }

    @Override
    String getName() {
        return LOADER_NAME.db
    }

    @Override
    void init(RuleConfig ruleConfig) {
        DbConfig config = ruleConfig.getDbConfig()
        dataSource = new DruidDataSource()
        dataSource.url = config.url
        dataSource.driverClassName = config.driverClassName
        dataSource.username = config.username
        dataSource.password = config.password
        dataSource.initialSize = config.initialSize
        dataSource.maxActive = config.maxActive
        dataSource.minIdle = config.minIdle
        dataSource.maxWait = config.maxWait
        dataSource.validationQuery = config.validationQuery
        dataSource.testOnBorrow = config.testOnBorrow
        dataSource.testOnReturn = config.testOnReturn
        dataSource.testWhileIdle = config.testWhileIdle
        dataSource.poolPreparedStatements = config.poolPreparedStatements
        dataSource.maxPoolPreparedStatementPerConnectionSize = config.maxPoolPreparedStatementPerConnectionSize
        tableName = config.tableName
        executeCreate(SqlUtil.buildCreateTableSql(config.driverClassName, tableName))
    }

    boolean add(Rule rule) {
        String sql = "insert into ${tableName} (id,name,code) values (?,?,?)"
        try {
            final byte[] serialize = serializer.serialize(rule.code)
            executeInsert(sql, createUUID(), rule.name, serialize)
            return true
        } catch (Exception e) {
            e.printStackTrace()
            return false
        }
    }

    boolean update(Rule rule) {
        String sql = "update ${tableName} set code = ? where name = ?"
        try {
            final byte[] serialize = serializer.serialize(rule.code)
            int i = executeUpdate(sql, serialize, rule.name)
            HandlerFactory.reloadHandler(rule.name)
            return i > 0
        } catch (Exception e) {
            e.printStackTrace()
            return false
        }
    }

    static String createUUID() {
        return String.valueOf(UUID.randomUUID().hashCode() & 0x7fffffff)
    }

    private int executeCreate(String sqlStr) {
        try {
            Sql sql = new Sql(dataSource)
            sql.execute(sqlStr)
            return 1
        } catch (Exception e) {
            logger.error("executeQuery-> " + e.getMessage())
        }
        return 0
    }

    private List<List<Object>> executeInsert(String sqlStr, Object... params) {
        Sql sql = new Sql(dataSource)
        return sql.executeInsert(sqlStr, params)
    }

    private int executeUpdate(String sqlStr, Object... params) {
        Sql sql = new Sql(dataSource)
        return sql.executeUpdate(sqlStr, params)
    }
}
