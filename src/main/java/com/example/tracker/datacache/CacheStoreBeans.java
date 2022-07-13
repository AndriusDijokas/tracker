package com.example.tracker.datacache;

import com.example.tracker.model.planing.Developer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheStoreBeans {

    @Bean
    public CacheStore<Developer> splintedTaskStorage() {
        return new CacheStore<Developer>(1, TimeUnit.DAYS);
    }
}
