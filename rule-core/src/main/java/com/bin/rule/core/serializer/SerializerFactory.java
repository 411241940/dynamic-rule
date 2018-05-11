package com.bin.rule.core.serializer;

/**
 * 序列化工具工厂
 * @author bin
 * @version 1.0 2018/5/10
 * */
public class SerializerFactory {

    private static Serializer serializer;

    private SerializerFactory() {
    }

    public static void setSerializer(Serializer serializer) {
        SerializerFactory.serializer = serializer;
    }

    public static Serializer getSerializer() {
        return serializer;
    }
}
