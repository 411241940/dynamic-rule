package com.bin.rule.core.bootstrap

import com.bin.rule.core.HandlerFactory
import com.bin.rule.core.config.RuleConfig
import com.bin.rule.core.loader.Loader
import com.bin.rule.core.util.SpringBeanUtil
import org.springframework.beans.BeansException
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.ConfigurableApplicationContext

/**
 * 启动类
 * @author bin
 * @version 1.0 2018/4/26
 * */
class RuleBootstrap extends RuleConfig implements ApplicationContextAware {

    private static ConfigurableApplicationContext applicationContext

    @Override
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext as ConfigurableApplicationContext
        init(this)
    }

    private static void init(RuleConfig config) {
        Loader loader = loadSpiSupport(config)
        HandlerFactory factory = new HandlerFactory(loader)
        SpringBeanUtil.setApplicationContext(applicationContext)
        applicationContext.getBeanFactory().registerSingleton(HandlerFactory.class.name, factory)
    }

    private static Loader loadSpiSupport(RuleConfig config) {
        final ServiceLoader<Loader> loaders = ServiceLoader.load(Loader.class)
        return loaders.find {
            it.getName() == config.loaderSupport
        } as Loader
    }
}
