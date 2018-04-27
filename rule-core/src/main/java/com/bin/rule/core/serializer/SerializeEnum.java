package com.bin.rule.core.serializer;

/**
 * 序列化枚举
 *
 * @author bin
 * @version 1.0 2018/4/27
 */
public enum SerializeEnum {

    /**
     * Jdk serialize protocol enum.
     */
    JDK("jdk"),

    /**
     * Kryo serialize protocol enum.
     */
    KRYO("kryo");

    private String serialize;

    SerializeEnum(String serialize) {
        this.serialize = serialize;
    }

    /**
     * Gets serialize protocol.
     *
     * @return the serialize protocol
     */
    public String getSerialize() {
        return serialize;
    }

    /**
     * Sets serialize protocol.
     *
     * @param serialize the serialize protocol
     */
    public void setSerialize(String serialize) {
        this.serialize = serialize;
    }


}
