package com.mitocode.product_service.controller;

import com.mitocode.product_service.entity.Producto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductoController {

    @GetMapping("/name")
    public String getName() {
        return "productName";
    }

    @GetMapping("/helloWorld")
    public String helloWorld() {
        return "Hello world from Service A!";
    }


    /*@FeignClient(name="PRODUCT-SERVICE", url="http://PRODUCT-SERVICE/product/name")
    public interface String getName();*/


}
