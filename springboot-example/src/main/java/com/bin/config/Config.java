package com.bin.config;

import com.bin.rule.core.HandlerFactory;
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
    public HandlerFactory handlerFactory() {
        return new HandlerFactory();
    }
}
