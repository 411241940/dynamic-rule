package com.bin.rule.core.handler;

import java.util.Map;

/**
 * 执行逻辑接口
 *
 * @author bin
 * @version 1.0 2018/4/25
 **/
public interface Handler {

    /**
     * 执行逻辑
     *
     * @param params 入参
     * @return 返回对象
     */
    Object handle(Map<String, Object> params);
}
