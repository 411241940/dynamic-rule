package com.bin;

import com.bin.rule.core.invoker.Invoker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

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

    @RequestMapping("/compare")
    Object compare(@RequestParam Integer score) {
        Map<String, Object> params = new HashMap<>();
        params.put("score", score);
        return Invoker.invoke("CompareHandler", params);
    }

    @RequestMapping("/add")
    Object add() {
        String code = "package rules;" +
                "import com.bin.rule.core.handler.Handler;" +
                "public class CompareHandler implements Handler {" +
                "public Object handle(Map<String, Object> params) {" +
                "return params.get(\"score\") < 10;" +
                "}" +
                "}" +
                ";";
        return Invoker.add("CompareHandler", code);
    }

    @RequestMapping("/update")
    Object update() {
        String code = "package rules;" +
                "import com.bin.rule.core.handler.Handler;" +
                "public class CompareHandler implements Handler {" +
                "public Object handle(Map<String, Object> params) {" +
                "return params.get(\"score\") < 15;" +
                "}" +
                "}" +
                ";";
        return Invoker.update("CompareHandler", code);
    }

}