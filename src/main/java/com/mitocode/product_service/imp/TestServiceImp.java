package com.mitocode.product_service.imp;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class TestServiceImp implements TestService {

    private Random random = new Random();

    @CircuitBreaker(name = "customerService", fallbackMethod = "fallback")
    @Retry(name = "productService")
    public String testCuricuit() {
        if (random.nextBoolean()) {
            throw new RuntimeException("Error o.O");
        }
        return "Respuesta exitosa: " + LocalDateTime.now();
    }

    public String fallback(Throwable t) {
        return "Fallback (Mensaje: " + t.getMessage() + ")";
    }
}
