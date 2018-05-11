package com.bin.rule.core.broadcast;

/**
 * 广播信息实体类
 * @author bin
 * @version 1.0 2018/5/10
 * */
public class BroadcastMessage {

    /**
     * 规则名称
     */
    private String ruleName;

    /**
     * 版本号
     */
    private long version;

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}
