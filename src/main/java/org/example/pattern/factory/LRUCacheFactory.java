package org.example.pattern.factory;

import org.example.cache.Cache;
import org.example.cache.LRUCache;

public class LRUCacheFactory<K, V> implements CacheFactory<K, V> {
    @Override
    public Cache<K, V> createCache() {
        return new LRUCache<>();
    }
}
