package com.bin.rule.core

import com.bin.rule.core.handler.Handler
import com.bin.rule.core.loader.Loader
import com.bin.rule.core.util.SpringBeanUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.annotation.AnnotationUtils

import javax.annotation.Resource
import java.lang.reflect.Field
import java.lang.reflect.Modifier
import java.util.concurrent.ConcurrentHashMap

/**
 * handler获取工厂
 * @author bin
 * @version 1.0 2018/4/25
 * */
class HandlerFactory {

    private Loader loader

    private GroovyClassLoader groovyClassLoader = new GroovyClassLoader()

    /**
     * handler实例缓存
     */
    private static final ConcurrentHashMap<String, Handler> HANDLER_INSTANCES = new ConcurrentHashMap<>()

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

        Handler handler = HANDLER_INSTANCES.get(name)
        if (!handler) {
            handler = createHandler(name)
            if (!handler) {
                throw new IllegalStateException("load handler error, handler(name: ${name}) is null")
            }
            HANDLER_INSTANCES.put(name, handler)
        }

        return handler
    }

    /**
     * 创建handler
     * @param name handler名称
     * @return Handler
     */
    private Handler createHandler(String name) {
        String code = loader.load(name)

        if (code != null && code.trim().length() > 0) {
            Class<?> clazz = groovyClassLoader.parseClass(code)
            if (clazz != null) {
                def handler = clazz.newInstance()
                if (handler != null && handler instanceof Handler) {
                    populateHandler(handler)
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
            if (AnnotationUtils.getAnnotation(field, Resource.class) != null) {
                Resource resource = AnnotationUtils.getAnnotation(field, Resource.class)
                if (resource.name() != null && resource.name().length() > 0) {
                    fieldBean = SpringBeanUtil.getApplicationContext().getBean(resource.name())
                } else {
                    fieldBean = SpringBeanUtil.getApplicationContext().getBean(field.getName())
                }
                if (!fieldBean) {
                    fieldBean = SpringBeanUtil.getApplicationContext().getBean(field.getType())
                }
            } else if (AnnotationUtils.getAnnotation(field, Autowired.class) != null) {
                Qualifier qualifier = AnnotationUtils.getAnnotation(field, Qualifier.class)
                if (qualifier != null && qualifier.value() != null && qualifier.value().length() > 0) {
                    fieldBean = SpringBeanUtil.getApplicationContext().getBean(qualifier.value())
                } else {
                    fieldBean = SpringBeanUtil.getApplicationContext().getBean(field.getType())
                }
            }

            if (fieldBean) {
                field.setAccessible(true)
                field.set(handler, fieldBean)
            }
        }
    }

//    /**
//     * 调用规则方法
//     * @param name handler名称
//     * @return 调用结果
//     */
//    static Object invoke(String name) {
//        return invoke(name, null)
//    }
//
//    /**
//     * 调用规则方法
//     * @param name handler名称
//     * @param params 参数
//     * @return 调用结果
//     */
//    static Object invoke(String name, Map<String, Object> params) {
//        return getHandler(name).handle(params)
//    }

}
