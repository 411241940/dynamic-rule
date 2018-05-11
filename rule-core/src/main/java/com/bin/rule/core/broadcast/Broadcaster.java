package com.bin.rule.core.broadcast;

import com.bin.rule.core.config.RuleConfig;

/**
 * 广播接口
 * @author bin
 * @version 1.0 2018/5/10
 * */
public interface Broadcaster {

    void init(RuleConfig ruleConfig);

    /**
     * 发送消息
     *
     * @param ruleName 规则名称
     * @param version  版本号
     * @return boolean
     */
    boolean produce(String ruleName, long version);

    /**
     * 监听
     *
     * @param name 规则名称
     * @return boolean
     */
    boolean watch(String name);

    /**
     * 消费
     *
     * @param data 数据包
     */
    void consume(byte[] data);

    /**
     * 设置name
     *
     * @return name
     */
    String getName();
}
