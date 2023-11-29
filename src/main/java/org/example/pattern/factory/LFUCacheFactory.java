package org.example.pattern.factory;

import org.example.cache.Cache;
import org.example.cache.LFUCache;

public class LFUCacheFactory<K, V> implements CacheFactory<K, V> {
    @Override
    public Cache<K, V> createCache() {
        return new LFUCache<>();
    }
}
