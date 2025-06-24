package com.mitocode.product_service.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CacheEvictScheduler {

    private final CacheManager cacheManager;

    public CacheEvictScheduler(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    //@Scheduled(cron = "0 * * * * *")
    @Scheduled(fixedRate = 60000)
    public void clearUserCache() {
        log.info("Ejecutando tarea clearUserCache");
        //cacheManager.getCache("products").clear();
        //cacheManager.getCache("productList").clear();
        /*cacheManager.getCacheNames()
             .forEach(name -> cacheManager.getCache(name).clear());*/
    }
}