package com.bin.rule.core.broadcast;

/**
 * 广播工厂
 * @author bin
 * @version 1.0 2018/5/10
 * */
public class BroadcasterFactory {

    private static Broadcaster broadcaster;

    private BroadcasterFactory() {
    }

    public static void setBroadcaster(Broadcaster broadcaster) {
        BroadcasterFactory.broadcaster = broadcaster;
    }

    public static Broadcaster getBroadcaster() {
        return broadcaster;
    }
}
