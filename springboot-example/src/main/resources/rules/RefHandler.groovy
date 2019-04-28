package rules

import com.bin.rule.core.handler.Handler

/**
 * todo
 *
 * @author huanglb
 * @create 2019/4/16
 */
class RefHandler implements Handler {

    private Handler helloHandler

    @Override
    Object handle(Map<String, Object> params) {
        return helloHandler.handle(params)
    }

}
