package com.bin.rule.core.loader.impl

import com.bin.rule.core.config.RuleConfig
import com.bin.rule.core.entity.Rule
import com.bin.rule.core.loader.Loader
import com.bin.rule.core.serializer.Serializer

/**
 * AbstractLoader
 * @author bin
 * @version 1.0 2018/4/27
 * */
class AbstractLoader implements Loader {

    protected Serializer serializer

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
    void setSerializer(Serializer serializer) {
        this.serializer = serializer
    }

    @Override
    boolean add(Rule rule) {
        return true
    }

    @Override
    boolean update(Rule rule) {
        return true
    }
}
