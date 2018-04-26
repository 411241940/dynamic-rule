package com.bin.config;

import com.bin.rule.core.bootstrap.RuleBootstrap;
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
        return ruleBootstrap;
    }
}
