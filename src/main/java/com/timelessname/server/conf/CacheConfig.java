package com.timelessname.server.conf;

import static java.util.concurrent.TimeUnit.*;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.cache.CacheBuilder;

@Configuration
@EnableCaching
public class CacheConfig {

  @Bean
  public CacheManager cacheManager() {
    GuavaCacheManager guavaCacheManager = new GuavaCacheManager();
    guavaCacheManager.setCacheBuilder(CacheBuilder.newBuilder()
        .expireAfterWrite(10, SECONDS));
    return guavaCacheManager;
  }
  
}
