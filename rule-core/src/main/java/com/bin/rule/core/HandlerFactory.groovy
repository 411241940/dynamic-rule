package com.bin.rule.core

import com.bin.rule.core.broadcast.BroadcasterFactory
import com.bin.rule.core.handler.Handler
import com.bin.rule.core.loader.Loader
import com.bin.rule.core.util.SpringBeanUtil
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.annotation.AnnotationUtils

import javax.annotation.Resource
import java.lang.reflect.Field
import java.lang.reflect.Modifier
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * handler获取工厂
 * @author bin
 * @version 1.0 2018/4/25
 * */
class HandlerFactory {

    private Logger LOGGER = LoggerFactory.getLogger(HandlerFactory.class)

    private Loader loader

    private GroovyClassLoader groovyClassLoader = new GroovyClassLoader()

    private Executor refreshExecutor = Executors.newFixedThreadPool(3)

    /**
     * handler实例缓存
     */
    private static final ConcurrentHashMap<String, Handler> HANDLER_INSTANCES = new ConcurrentHashMap<>()

    /**
     * 循环依赖缓存
     */
    private final Map<String, Handler> EARLY_HANDLER_INSTANCES = new ConcurrentHashMap<>()

    HandlerFactory() {
    }

    HandlerFactory(Loader loader) {
        this.loader = loader
    }

    /**
     * 获取handler
     * @param name handler名称
     * @return Handler
     */
    Handler getHandler(String name) {
        if (name == null || name.trim().length() == 0) {
            return null
        }

        // 先从缓存获取
        Handler handler = HANDLER_INSTANCES.get(name)
        if (handler != null) {
            return handler
        }

        // handler是否创建中，解决循环依赖
        Handler earlyBean = EARLY_HANDLER_INSTANCES.get(name)
        if (earlyBean != null) {
            return earlyBean
        }

        synchronized (HandlerFactory.class) {
            handler = HANDLER_INSTANCES.get(name)
            if (handler != null) {
                return handler
            }
            handler = createHandler(name)
        }

        if (handler == null) {
            throw new IllegalStateException("load handler error, handler(name: ${name}) is null")
        }

        // 循环依赖缓存
        EARLY_HANDLER_INSTANCES.put(name, handler)

        // 依赖注入
        populateHandler(handler)

        // 缓存 handler 实例
        HANDLER_INSTANCES.put(name, handler)

        // 清除循环依赖缓存
        EARLY_HANDLER_INSTANCES.remove(name)

        // 监听广播
        BroadcasterFactory.broadcaster.watch(name)

        return handler
    }

    /**
     * 创建handler
     * @param name handler名称
     * @return Handler
     */
    private Handler createHandler(String name) {
        String code = loader.load(name)

        if (code) {
            Class<?> clazz = groovyClassLoader.parseClass(code)
            if (clazz) {
                def handler = clazz.newInstance()
                if (handler && handler instanceof Handler) {
                    return handler
                }
            }
        }
        return null
    }

    /**
     * 依赖注入
     * @param handler handler
     * @throws Exception Exception
     */
    @SuppressWarnings("GroovyAssignabilityCheck")
    private static void populateHandler(Object handler) throws Exception {
        if (!handler) {
            return
        }

        Field[] fields = handler.getClass().getDeclaredFields()
        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue
            }

            Object fieldBean = null
            if (AnnotationUtils.getAnnotation(field, Resource.class)) {
                Resource resource = AnnotationUtils.getAnnotation(field, Resource.class)
                if (resource.name() != null && resource.name().length() > 0) {
                    fieldBean = SpringBeanUtil.getApplicationContext().getBean(resource.name())
                } else {
                    fieldBean = SpringBeanUtil.getApplicationContext().getBean(field.getName())
                }
                if (!fieldBean) {
                    fieldBean = SpringBeanUtil.getApplicationContext().getBean(field.getType())
                }
            } else if (AnnotationUtils.getAnnotation(field, Autowired.class)) {
                Qualifier qualifier = AnnotationUtils.getAnnotation(field, Qualifier.class)
                if (qualifier != null && qualifier.value() != null && qualifier.value().length() > 0) {
                    fieldBean = SpringBeanUtil.getApplicationContext().getBean(qualifier.value())
                } else {
                    fieldBean = SpringBeanUtil.getApplicationContext().getBean(field.getType())
                }
            } else if (Handler.isAssignableFrom(field.getType())) {
                HandlerFactory factory = SpringBeanUtil.getBean(HandlerFactory.class) as HandlerFactory
                String handlerName = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1)
                fieldBean = factory.getHandler(handlerName)
            }

            if (fieldBean) {
                field.setAccessible(true)
                field.set(handler, fieldBean)
            }
        }
    }

    /**
     * 重新加载handler
     * @param name handler名称
     */
    void reloadHandler(String name) {
        if (name) {
            refreshExecutor.execute({
                Handler handler = createHandler(name)
                if (!handler) {
                    throw new IllegalStateException("reload handler error, handler(name: ${name}) is null")
                }
                HANDLER_INSTANCES.put(name, handler)
            })
        }
    }

}
