package com.bin.rule.core.loader.impl

import com.bin.rule.core.config.RedisConfig
import com.bin.rule.core.config.RuleConfig
import com.bin.rule.core.entity.Rule
import com.bin.rule.core.serializer.SerializerFactory
import redis.clients.jedis.JedisPool
import redis.clients.jedis.JedisPoolConfig

/**
 * redis代码加载器
 *
 * @author bin
 * @version 1.0 2018/4/25
 * */
class RedisLoader extends AbstractLoader {

    private JedisPool jedisPool

    @Override
    String load(String name) {
        return SerializerFactory.serializer.deSerialize(jedisPool.getResource().get(name.getBytes()), String.class)
    }

    @Override
    String getName() {
        return LOADER_NAME.redis
    }

    @Override
    void init(RuleConfig ruleConfig) {
        RedisConfig redisConfig = ruleConfig.getRedisConfig()

        JedisPoolConfig config = new JedisPoolConfig()
        config.setMaxIdle(redisConfig.getMaxIdle())
        //最小空闲连接数, 默认0
        config.setMinIdle(redisConfig.getMinIdle())
        //最大连接数, 默认8个
        config.setMaxTotal(redisConfig.getMaxTotal())
        //获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
        config.setMaxWaitMillis(redisConfig.getMaxWaitMillis())
        //在获取连接的时候检查有效性, 默认false
        config.setTestOnBorrow(redisConfig.getTestOnBorrow())
        //返回一个jedis实例给连接池时，是否检查连接可用性（ping()）
        config.setTestOnReturn(redisConfig.getTestOnReturn())
        //在空闲时检查有效性, 默认false
        config.setTestWhileIdle(redisConfig.getTestWhileIdle())
        //逐出连接的最小空闲时间 默认1800000毫秒(30分钟 )
        config.setMinEvictableIdleTimeMillis(redisConfig.getMinEvictableIdleTimeMillis())
        //对象空闲多久后逐出, 当空闲时间>该值 ，且 空闲连接>最大空闲数 时直接逐出,不再根据MinEvictableIdleTimeMillis判断  (默认逐出策略)，默认30m
        config.setSoftMinEvictableIdleTimeMillis(redisConfig.getSoftMinEvictableIdleTimeMillis())
        //逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
        config.setTimeBetweenEvictionRunsMillis(redisConfig.getTimeBetweenEvictionRunsMillis())
        //每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
        config.setNumTestsPerEvictionRun(redisConfig.getNumTestsPerEvictionRun())

        if (redisConfig.password) {
            jedisPool = new JedisPool(config, redisConfig.hostName, redisConfig.port, redisConfig.timeOut, redisConfig.password)
        } else {
            jedisPool = new JedisPool(config, redisConfig.hostName, redisConfig.port, redisConfig.timeOut)
        }
    }

    @Override
    boolean add(Rule rule) {
        final byte[] serialize = SerializerFactory.serializer.serialize(rule.code)
        jedisPool.getResource().set(rule.name.getBytes(), serialize)
        return true
    }

    @Override
    boolean updateCode(Rule rule) {
        final byte[] serialize = SerializerFactory.serializer.serialize(rule.code)
        jedisPool.getResource().set(rule.name.getBytes(), serialize)
        return true
    }
}
