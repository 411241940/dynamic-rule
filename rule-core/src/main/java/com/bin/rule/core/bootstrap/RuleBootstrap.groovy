package com.bin.rule.core.bootstrap

import com.bin.rule.core.HandlerFactory
import com.bin.rule.core.broadcast.Broadcaster
import com.bin.rule.core.broadcast.BroadcasterFactory
import com.bin.rule.core.broadcast.zk.ZkBroadcaster
import com.bin.rule.core.config.RuleConfig
import com.bin.rule.core.loader.Loader
import com.bin.rule.core.serializer.Serializer
import com.bin.rule.core.serializer.SerializerFactory
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

    private ConfigurableApplicationContext applicationContext

    @Override
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext as ConfigurableApplicationContext
        init(this)
    }

    private void init(RuleConfig config) {
        loadSpiSupport(config)
        SpringBeanUtil.setApplicationContext(this.applicationContext)
    }

    private void loadSpiSupport(RuleConfig config) {
        // 加载Loader
        final ServiceLoader<Loader> loaders = ServiceLoader.load(Loader.class)
        Loader loader = loaders.find {
            it.name == config.loaderSupport
        } as Loader
        loader.init(config)
        this.applicationContext.getBeanFactory().registerSingleton(Loader.class.name, loader)

        // 初始化HandlerFactory
        HandlerFactory factory = new HandlerFactory(loader)
        this.applicationContext.getBeanFactory().registerSingleton(HandlerFactory.class.name, factory)

        // 加载Serializer
        final ServiceLoader<Serializer> serializers = ServiceLoader.load(Serializer.class)
        Serializer serializer = serializers.find {
            it.name == config.serializer
        } as Serializer
        SerializerFactory.serializer = serializer

        // 加载Broadcaster
        final ServiceLoader<Broadcaster> broadcasters = ServiceLoader.load(Broadcaster.class)
        Broadcaster broadcaster = broadcasters.find {
            it.name == config.broadcaster
        } as Broadcaster
        broadcaster.init(config)
        BroadcasterFactory.broadcaster = broadcaster

    }
}
