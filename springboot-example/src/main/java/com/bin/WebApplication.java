package com.bin;

import com.bin.rule.core.invoker.Invoker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableAutoConfiguration
@ComponentScan
@RestController
@SpringBootApplication
@EnableScheduling
public class WebApplication extends SpringBootServletInitializer {

    @RequestMapping("/")
    Object hello() {
        return Invoker.invoke("HelloHandler");
    }

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}