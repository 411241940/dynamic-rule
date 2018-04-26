package rules

import com.bin.rule.core.handler.Handler
import com.bin.service.HelloService

import javax.annotation.Resource

/**
 * todo
 * @author bin
 * @version 1.0 2018/4/25
 * */
class HelloHandler implements Handler {

    @Resource
    private HelloService helloService

    @Override
    Object handle(Map<String, Object> params) {
        return helloService.hello()
    }

}
