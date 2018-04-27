package com.bin.rule.core.serializer.impl;


import com.bin.rule.core.serializer.SerializeEnum;
import com.bin.rule.core.serializer.Serializer;

import java.io.*;

/**
 * java序列化工具
 *
 * @author bin
 * @version 1.0 2018/4/27
 */
public class JavaSerializer implements Serializer {
    @Override
    public byte[] serialize(Object obj) throws Exception {
        try (ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream()
             ; ObjectOutput objectOutput = new ObjectOutputStream(arrayOutputStream);) {
            objectOutput.writeObject(obj);
            objectOutput.flush();
            objectOutput.close();
            return arrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new Exception("JAVA serialize error " + e.getMessage());
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T deSerialize(byte[] param, Class<T> clazz) throws Exception {
        ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(param);
        try {
            ObjectInput input = new ObjectInputStream(arrayInputStream);
            return (T) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new Exception("JAVA deSerialize error " + e.getMessage());
        }
    }

    @Override
    public String getName() {
        return SerializeEnum.JDK.getSerialize();
    }
}
