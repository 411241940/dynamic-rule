package com.bin.rule.core.util

import org.springframework.beans.BeansException
import org.springframework.context.ApplicationContext

/**
 * spring bean操作工具类
 * @author bin
 * @version 1.0 2018/4/26
 * */
class SpringBeanUtil {
    private static ApplicationContext context

    static void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext
    }

    static ApplicationContext getApplicationContext() {
        return context
    }

    static Object getBean(Class type) {
        return context.getBean(type)
    }
}
