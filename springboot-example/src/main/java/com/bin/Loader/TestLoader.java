package com.bin.Loader;

import com.bin.rule.core.loader.Loader;

/**
 * loader扩展测试
 *
 * @author bin
 * @version 1.0 2018/4/26
 **/
public class TestLoader implements Loader {
    @Override
    public String load(String name) {
        return "package rules;" +
                "import com.bin.rule.core.handler.Handler;" +
                "public class TestHandler implements Handler {" +
                "public Object handle(Map<String, Object> params) {" +
                "return 'test';" +
                "}" +
                "}" +
                ";";
    }

    @Override
    public String getName() {
        return "test";
    }
}
