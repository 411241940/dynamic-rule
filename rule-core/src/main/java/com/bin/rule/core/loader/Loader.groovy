package com.bin.rule.core.loader

import com.bin.rule.core.config.RuleConfig
import com.bin.rule.core.entity.Rule
import com.bin.rule.core.serializer.Serializer

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

    /**
     * 初始化
     * @param ruleConfig 配置
     */
    void init(RuleConfig ruleConfig)

    /**
     * 设置序列化
     *
     * @param serializer 序列化实现
     */
    void setSerializer(Serializer serializer)

    /**
     * 保存规则
     * @param rule 规则实体
     * @return boolean
     */
    boolean add(Rule rule)

    /**
     * 更新规则
     * @param rule 规则实体
     * @return boolean
     */
    boolean update(Rule rule)
}