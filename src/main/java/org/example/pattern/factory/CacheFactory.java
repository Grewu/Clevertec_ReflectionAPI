package org.example.pattern.factory;

import org.example.cache.Cache;

public interface CacheFactory<K, V> {
    Cache<K, V> createCache();
}
