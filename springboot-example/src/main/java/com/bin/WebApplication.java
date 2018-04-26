package com.bin;

import com.bin.rule.core.HandlerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@EnableAutoConfiguration
@ComponentScan
@RestController
@SpringBootApplication
@EnableScheduling
public class WebApplication extends SpringBootServletInitializer {

    @Resource
    private HandlerFactory handlerFactory;

    @RequestMapping("/")
    Object hello() {
        return handlerFactory.invoke("ConfigHandler");
    }

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}