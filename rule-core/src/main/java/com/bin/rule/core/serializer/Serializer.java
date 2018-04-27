package com.bin.rule.core.serializer;


/**
 * 序列化工具
 *
 * @author bin
 * @version 1.0 2018/4/27
 */
public interface Serializer {
    /**
     * 序列化对象
     *
     * @param obj 需要序更列化的对象
     * @return byte []
     * @throws Exception 异常信息
     */
    byte[] serialize(Object obj) throws Exception;


    /**
     * 反序列化对象
     *
     * @param param 需要反序列化的byte []
     * @param clazz java对象
     * @param <T>   泛型支持
     * @return 对象
     * @throws Exception 异常信息
     */
    <T> T deSerialize(byte[] param, Class<T> clazz) throws Exception;


    /**
     * 设置name
     *
     * @return name
     */
    String getName();
}
