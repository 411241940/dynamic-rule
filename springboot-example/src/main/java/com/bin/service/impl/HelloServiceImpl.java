package com.bin.service.impl;

import com.bin.service.HelloService;
import org.springframework.stereotype.Service;

/**
 * todo
 *
 * @author bin
 * @version 1.0 2018/4/26
 **/
@Service("helloService")
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello() {
        return "hello";
    }
}
