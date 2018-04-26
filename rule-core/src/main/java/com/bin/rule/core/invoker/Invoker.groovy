package com.bin.rule.core.invoker

import com.bin.rule.core.HandlerFactory
import com.bin.rule.core.handler.Handler
import com.bin.rule.core.util.SpringBeanUtil

/**
 * 调用类
 * @author bin
 * @version 1.0 2018/4/26
 * */
class Invoker {

    /**
     * 调用规则方法
     * @param name handler名称
     * @return 调用结果
     */
    static Object invoke(String name) {
        return invoke(name, null)
    }

    /**
     * 调用规则方法
     * @param name handler名称
     * @param params 参数
     * @return 调用结果
     */
    static Object invoke(String name, Map<String, Object> params) {
        HandlerFactory factory = SpringBeanUtil.getBean(HandlerFactory.class) as HandlerFactory
        factory.getHandler(name).handle(params)
    }
}
