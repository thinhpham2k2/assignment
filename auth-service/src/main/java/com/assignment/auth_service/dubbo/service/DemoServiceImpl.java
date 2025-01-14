package com.assignment.auth_service.dubbo.service;

import com.assignment.common_library.service.DemoService;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class DemoServiceImpl implements DemoService {

    @Override
    public String sayHello(String name) {

        System.out.println("Hello " + name);
        return  "Hello " + name;
    }
}
