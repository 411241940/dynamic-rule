package com.bin.config;

import com.bin.rule.core.bootstrap.RuleBootstrap;
import com.bin.rule.core.config.DbConfig;
import com.bin.rule.core.config.RedisConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * todo
 *
 * @author bin
 * @version 1.0 2018/4/25
 **/
@Configuration
public class Config {
    @Bean
    public RuleBootstrap ruleBootstrap() {
        RuleBootstrap ruleBootstrap = new RuleBootstrap();
        ruleBootstrap.setLoaderSupport("file");

//        DbConfig dbConfig = new DbConfig();
//        dbConfig.setUrl("jdbc:mysql://127.0.0.1:3306/myth?useUnicode=true&characterEncoding=utf8");
//        dbConfig.setDriverClassName("com.mysql.jdbc.Driver");
//        dbConfig.setUsername("root");
//        dbConfig.setPassword("buzhidao");
//        ruleBootstrap.setDbConfig(dbConfig);

//        RedisConfig redsConfig = new RedisConfig();
//        redsConfig.setHostName("127.0.0.1");
//        redsConfig.setPort(6379);
//        ruleBootstrap.setRedisConfig(redsConfig);
        return ruleBootstrap;
    }
}
