package rules

import com.bin.rule.core.handler.Handler

/**
 * todo
 * @author bin
 * @version 1.0 2018/4/25
 * */
class MethodHandler implements Handler {
    Object handle(Map<String, Object> params) {
        def list = [1..10]
        return list
    }
}
