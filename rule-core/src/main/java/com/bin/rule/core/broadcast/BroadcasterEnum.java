package com.bin.rule.core.broadcast;

/**
 * 广播枚举
 * @author bin
 * @version 1.0 2018/5/10
 * */
public enum BroadcasterEnum {

    ZK("zk");

    private String broadcaster;

    BroadcasterEnum(String broadcaster) {
        this.broadcaster = broadcaster;
    }

    /**
     * Gets serialize protocol.
     *
     * @return the serialize protocol
     */
    public String getBroadcaster() {
        return broadcaster;
    }


}
