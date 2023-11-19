package org.example.cache;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public final class LRUCache<K, V> implements Cache<K, V> {

    private final int capacity;
    private final LinkedHashMap<K, V> map;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.map = new LinkedHashMap<>(capacity, 0.75f, true);
    }

    public LRUCache() {
        this.capacity = 10;
        this.map = new LinkedHashMap<>(capacity, 0.75f, true);
    }

    @Override
    public V get(K key) {
        return map.getOrDefault(key, null);
    }

    @Override
    public void set(K key, V value) {
        if (!map.containsKey(key) && map.size() == capacity) {
            Iterator<Map.Entry<K, V>> iterator = map.entrySet().iterator();
            iterator.next();
            iterator.remove();
        }
        map.put(key, value);
    }

    @Override
    public void remove(K key) {
        map.remove(key);
    }


}