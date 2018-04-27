package com.bin.rule.core.config

/**
 * redis配置类
 * @author bin
 * @version 1.0 2018/4/27
 * */
class RedisConfig {

    String hostName

    int port

    String password

    int maxTotal = 8

    int maxIdle = 8

    int minIdle = 0

    long maxWaitMillis = -1L

    long minEvictableIdleTimeMillis = 1800000L

    long softMinEvictableIdleTimeMillis = 1800000L

    int numTestsPerEvictionRun = 3

    Boolean testOnCreate = false

    Boolean testOnBorrow = false

    Boolean testOnReturn = false

    Boolean testWhileIdle = false

    long timeBetweenEvictionRunsMillis = -1L

    boolean blockWhenExhausted = true

    int timeOut = 10000

    Boolean cluster = false

    String clusterUrl
}
