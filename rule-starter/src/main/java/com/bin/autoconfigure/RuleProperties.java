package com.bin.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 配置实体
 *
 * @author bin
 * @version 1.0 2018/5/9
 */
@ConfigurationProperties(prefix = "rule")
public class RuleProperties {

    // 选择使用的 Loader ,默认为 FileLoader
    private String loader = "file";

    // 选择使用的 Serializer ,默认为 KryoSerializer
    private String serializer = "kryo";

    // redis 相关配置
    private String redisHost;
    private int redisPort;
    private String redisPassword;

    // db 相关配置
    private String dbUrl;
    private String dbDriverClassName;
    private String dbUsername;
    private String dbPassword;

    // 选择使用的 Broadcaster ,默认为 ZkBroadcaster
    private String broadcaster = "zk";

     // zk 配置
    private String zkServer;

    public String getLoader() {
        return loader;
    }

    public void setLoader(String loader) {
        this.loader = loader;
    }

    public String getSerializer() {
        return serializer;
    }

    public void setSerializer(String serializer) {
        this.serializer = serializer;
    }

    public String getRedisHost() {
        return redisHost;
    }

    public void setRedisHost(String redisHost) {
        this.redisHost = redisHost;
    }

    public int getRedisPort() {
        return redisPort;
    }

    public void setRedisPort(int redisPort) {
        this.redisPort = redisPort;
    }

    public String getRedisPassword() {
        return redisPassword;
    }

    public void setRedisPassword(String redisPassword) {
        this.redisPassword = redisPassword;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getDbDriverClassName() {
        return dbDriverClassName;
    }

    public void setDbDriverClassName(String dbDriverClassName) {
        this.dbDriverClassName = dbDriverClassName;
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public void setDbUsername(String dbUsername) {
        this.dbUsername = dbUsername;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getBroadcaster() {
        return broadcaster;
    }

    public void setBroadcaster(String broadcaster) {
        this.broadcaster = broadcaster;
    }

    public String getZkServer() {
        return zkServer;
    }

    public void setZkServer(String zkServer) {
        this.zkServer = zkServer;
    }
}
