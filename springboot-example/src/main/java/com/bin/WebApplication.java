package com.bin;

import com.bin.rule.core.entity.Rule;
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

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

    @RequestMapping("/")
    Object hello() {
        return Invoker.invoke("HelloHandler");
    }

    @RequestMapping("/add")
    Object add() {
        Rule rule = new Rule();
        rule.setName("test");
        String code = "package rules;" +
                "import com.bin.rule.core.handler.Handler;" +
                "public class TestHandler implements Handler {" +
                "public Object handle(Map<String, Object> params) {" +
                "return 'test';" +
                "}" +
                "}" +
                ";";
        rule.setCode(code);
        return Invoker.add(rule);
    }

    @RequestMapping("/update")
    Object update() {
        Rule rule = new Rule();
        rule.setName("HelloHandler");
        String code = "package rules;" +
                "import com.bin.rule.core.handler.Handler;" +
                "public class TestHandler implements Handler {" +
                "public Object handle(Map<String, Object> params) {" +
                "return 'test123';" +
                "}" +
                "}" +
                ";";
        rule.setCode(code);
        return Invoker.update(rule);
    }

}