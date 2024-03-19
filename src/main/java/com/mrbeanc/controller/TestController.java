package com.mrbeanc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    /** 测试接口 用于判断服务正常否
     * Get请求也隐式支持HEAD请求，减少带宽消耗
     * */
    @GetMapping("/test")
    public String test() {
        return "Hello, World!";
    }
}
