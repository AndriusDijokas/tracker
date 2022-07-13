package com.example.tracker.datacache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class CacheStore<T> {
    private static final Logger log = LoggerFactory.getLogger(CacheStore.class);
    private Cache<String, T> cache;

    //Constructor to build Cache Store
    public CacheStore(int expiryDuration, TimeUnit timeUnit) {
        cache = CacheBuilder.newBuilder()
                .expireAfterWrite(expiryDuration, timeUnit)
                .concurrencyLevel(Runtime.getRuntime().availableProcessors())
                .build();
    }

    //Method to fetch previously stored record using record key
    public T get(String key) {
        return cache.getIfPresent(key);
    }

    public void remove(String key){
        cache.invalidate(key);
    }

    //Method to put a new record in Cache Store with record key
    public void add(String key, T value) {
        if (key != null && value != null) {
            cache.put(key, value);
            log.info("Record stored in {} Cache with Key = {}", value.getClass().getSimpleName(), key);
        }
    }

    public Map<String, T> getAsMap() {
       return cache.asMap();
    }

    public void remove(Set<String> keys) {
        cache.invalidateAll(keys);
    }
}
