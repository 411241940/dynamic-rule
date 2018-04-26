package com.bin.rule.core.loader

/**
 * 代码加载器
 *
 * @author bin
 * @version 1.0 2018/4/25
 * */
interface Loader {

    static final def LOADER_NAME = [
            db   : "db",
            file : "file",
            redis: "redis"
    ]

    /**
     * 根据handler名称加载代码
     *
     * @param name handler名称
     * @return handler代码
     */
    String load(String name)

    /**
     * 加载器名称
     *
     * @param name handler名称
     * @return handler代码
     */
    String getName()
}