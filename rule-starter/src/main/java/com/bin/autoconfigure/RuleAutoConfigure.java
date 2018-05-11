package com.bin.autoconfigure;

import com.bin.rule.core.bootstrap.RuleBootstrap;
import com.bin.rule.core.broadcast.BroadcasterEnum;
import com.bin.rule.core.config.DbConfig;
import com.bin.rule.core.config.RedisConfig;
import com.bin.rule.core.config.ZkConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * 自动配置
 *
 * @author bin
 * @version 1.0 2018/5/9
 */
@Configuration
@EnableConfigurationProperties(RuleProperties.class) //开启使用映射实体对象
public class RuleAutoConfigure {

    @Resource
    private RuleProperties ruleProperties; // 前缀配置映射实体对象

    @Bean
    @ConditionalOnProperty(prefix = "rule", name = "loader")
    public RuleBootstrap ruleBootstrap() {
        RuleBootstrap ruleBootstrap = new RuleBootstrap();
        ruleBootstrap.setLoaderSupport(ruleProperties.getLoader());
        ruleBootstrap.setSerializer(ruleProperties.getSerializer());

        if ("redis".equals(ruleProperties.getLoader())) {
            RedisConfig redsConfig = new RedisConfig();
            redsConfig.setHostName(ruleProperties.getRedisHost());
            redsConfig.setPort(ruleProperties.getRedisPort());
            redsConfig.setPassword(ruleProperties.getRedisPassword());
            ruleBootstrap.setRedisConfig(redsConfig);
        }

        if ("db".equals(ruleProperties.getLoader())) {
            DbConfig dbConfig = new DbConfig();
            dbConfig.setUrl(ruleProperties.getDbUrl());
            dbConfig.setDriverClassName(ruleProperties.getDbDriverClassName());
            dbConfig.setUsername(ruleProperties.getDbUsername());
            dbConfig.setPassword(ruleProperties.getDbPassword());
            ruleBootstrap.setDbConfig(dbConfig);
        }

        if (BroadcasterEnum.ZK.getBroadcaster().equals(ruleProperties.getLoader())) {
            ZkConfig zkConfig = new ZkConfig();
            zkConfig.setZkServer(ruleProperties.getZkServer());
            ruleBootstrap.setZkConfig(zkConfig);
        }

        return ruleBootstrap;
    }
}
