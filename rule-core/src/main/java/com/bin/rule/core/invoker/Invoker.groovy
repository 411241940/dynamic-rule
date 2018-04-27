package com.bin.rule.core.invoker

import com.bin.rule.core.HandlerFactory
import com.bin.rule.core.entity.Rule
import com.bin.rule.core.loader.Loader
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

    /**
     * 添加规则
     * @param name 规则名称
     * @param code 规则代码
     * @return 操作结果
     */
    static boolean add(String name, String code) {
        Rule rule = new Rule()
        rule.name = name
        rule.code = code
        return add(rule)
    }

    /**
     * 添加规则
     * @param rule 规则实体
     * @return 操作结果
     */
    static boolean add(Rule rule) {
        Loader loader = SpringBeanUtil.getBean(Loader.class) as Loader
        return loader.add(rule)
    }

    /**
     * 更新规则
     * @param name 规则名称
     * @param code 规则代码
     * @return 操作结果
     */
    static boolean update(String name, String code) {
        Rule rule = new Rule()
        rule.name = name
        rule.code = code
        return update(rule)
    }

    /**
     * 更新规则
     * @param rule 规则实体
     * @return 操作结果
     */
    static boolean update(Rule rule) {
        Loader loader = SpringBeanUtil.getBean(Loader.class) as Loader
        return loader.update(rule)
    }
}
