package com.bin.rule.core.serializer.impl;

import com.bin.rule.core.serializer.SerializeEnum;
import com.bin.rule.core.serializer.Serializer;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;


/**
 * kryo序列化工具
 *
 * @author bin
 * @version 1.0 2018/4/27
 */
public class KryoSerializer implements Serializer {

    /**
     * 序列化
     *
     * @param obj 需要序更列化的对象
     * @return 序列化后的byte 数组
     * @throws Exception 异常
     */
    @Override
    public byte[] serialize(Object obj) throws Exception {
        byte[] bytes;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            //获取kryo对象
            Kryo kryo = new Kryo();
            Output output = new Output(outputStream);
            kryo.writeObject(output, obj);
            bytes = output.toBytes();
            output.flush();
        } catch (Exception ex) {
            throw new Exception("kryo serialize error" + ex.getMessage());
        }
        return bytes;
    }

    /**
     * 反序列化
     *
     * @param param 需要反序列化的byte []
     * @return 序列化对象
     * @throws Exception 异常
     */
    @Override
    public <T> T deSerialize(byte[] param, Class<T> clazz) throws Exception {
        T object;
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(param)) {
            Kryo kryo = new Kryo();
            Input input = new Input(inputStream);
            object = kryo.readObject(input, clazz);
            input.close();
        } catch (Exception e) {
            throw new Exception("kryo deSerialize error" + e.getMessage());
        }
        return object;
    }

    @Override
    public String getName() {
        return SerializeEnum.KRYO.getSerialize();
    }
}
