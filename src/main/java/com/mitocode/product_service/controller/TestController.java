package com.mitocode.product_service.controller;

import com.mitocode.product_service.imp.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/test")
@RestController
@Slf4j
public class TestController {


    @Autowired
    TestService testService;

    @GetMapping("/circuit")
    public String testCircuitBreaker() {
        return testService.testCuricuit();
    }
}
