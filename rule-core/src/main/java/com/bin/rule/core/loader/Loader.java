package com.bin.rule.core.loader;

/**
 * 代码加载器
 *
 * @author bin
 * @version 1.0 2018/4/25
 **/
public interface Loader {

    /**
     * 根据handler名称加载代码
     * @param name handler名称
     * @return handler代码
     */
    String load(String name);
}
