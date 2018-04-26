package com.bin.rule.core.loader.impl

import com.bin.rule.core.loader.Loader

/**
 * 文件代码加载器
 * @author bin
 * @version 1.0 2018/4/25
 * */
class FileLoader implements Loader {

    private static final String BASE_DIR = "rules/"

    String load(String name) {
        if (name == null || name.trim().length() == 0) {
            return null
        }

        if (!name.contains(".groovy")) {
            name = name.concat(".groovy")
        }

        StringBuilder code = new StringBuilder()
        new File(Thread.currentThread().getContextClassLoader().getResource(BASE_DIR).getPath(), name).eachLine {
            code << it
            code << "\n"
        }
        return code
    }
}
