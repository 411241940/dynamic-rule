package com.bin.rule.core.config

/**
 * 配置类
 * @author bin
 * @version 1.0 2018/4/26
 * */
class RuleConfig {

    /**
     * 加载器选择
     */
    String loaderSupport = "file"

    /**
     * 序列化方式选择
     */
    String serializer = "kryo"

    /**
     * db配置
     */
    DbConfig dbConfig

    /**
     * redis配置
     */
    RedisConfig redisConfig
}
