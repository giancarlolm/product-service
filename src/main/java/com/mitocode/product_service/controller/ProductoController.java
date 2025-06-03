package com.mitocode.product_service.controller;

import com.mitocode.product_service.entity.Producto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductoController {
    // Inyectamos el contexto que sabe el WebServer y su puerto
    private final WebServerApplicationContext webServerAppCtxt;

    public ProductoController(WebServerApplicationContext webServerAppCtxt) {
        this.webServerAppCtxt = webServerAppCtxt;
    }

    @GetMapping("/name")
    public String getName() {
        return "productName";
    }

    @GetMapping("/helloWorld")
    public String helloWorld() {
        return "Hello world from Service A!";
    }

    @GetMapping("hola")
    public String Hola() {
        return "Hola desde Producto " + webServerAppCtxt.getWebServer().getPort();
    }


}
