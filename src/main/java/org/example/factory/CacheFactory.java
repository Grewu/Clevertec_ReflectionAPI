package org.example.factory;

import org.example.cache.Cache;

public interface CacheFactory<K, V> {
    Cache<K, V> createCache();
}
