package com.bin.rule.core.handler;

import java.util.Map;

/**
 * todo
 *
 * @author bin
 * @version 1.0 2018/4/25
 **/
public interface Handler {
    Object handle(Map<String, Object> params);
}
