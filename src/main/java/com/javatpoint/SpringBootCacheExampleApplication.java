package com.javatpoint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//enabling caching
@EnableCaching
public class SpringBootCacheExampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootCacheExampleApplication.class, args);
    }
    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("simpleCache");
    }
}
