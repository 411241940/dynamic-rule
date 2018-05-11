package com.bin.rule.core.loader.impl

import com.bin.rule.core.broadcast.BroadcasterFactory
import com.bin.rule.core.config.RuleConfig
import com.bin.rule.core.entity.Rule
import com.bin.rule.core.loader.Loader

/**
 * AbstractLoader
 * @author bin
 * @version 1.0 2018/4/27
 * */
abstract class AbstractLoader implements Loader {

    @Override
    String load(String name) {
        return null
    }

    @Override
    String getName() {
        return null
    }

    @Override
    void init(RuleConfig ruleConfig) {

    }

    @Override
    boolean add(Rule rule) {
        return true
    }

    @Override
    boolean update(Rule rule) {
        BroadcasterFactory.broadcaster.produce(rule.name, System.currentTimeMillis())
        updateCode(rule)
        return true
    }

    abstract boolean updateCode(Rule rule)
}
