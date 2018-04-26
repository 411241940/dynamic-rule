import com.bin.rule.core.handler.Handler
import com.bin.rule.core.HandlerFactory

/**
 * todo
 * @author bin
 * @version 1.0 2018/4/25
 * */
class HandlerFactoryTest {
    static void main(String[] args) {
        HandlerFactory factory = new HandlerFactory()
        Handler handler = factory.getHandler("MethodHandler")
        println handler.handle()
    }
}
