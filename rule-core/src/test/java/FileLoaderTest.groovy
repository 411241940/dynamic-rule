import com.bin.rule.core.loader.impl.FileLoader

/**
 * todo
 * @author bin
 * @version 1.0 2018/4/25
 * */
class FileLoaderTest {
    static void main(String[] args) {
        FileLoader loader = new FileLoader()
        String code = loader.load("MethodHandler")
        println code
    }
}
