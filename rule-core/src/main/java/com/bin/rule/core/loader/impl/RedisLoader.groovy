package com.bin.rule.core.loader.impl

import com.bin.rule.core.loader.Loader

/**
 * redis代码加载器
 *
 * @author bin
 * @version 1.0 2018/4/25
 * */
class RedisLoader implements Loader {

    @Override
    String load(String name) {
        // 查询redis中的代码段
        return null
    }

    @Override
    String getName() {
        return LOADER_NAME.redis
    }
}
