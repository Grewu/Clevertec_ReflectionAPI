package org.example.cache;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public final class LFUCache<K, V> implements Cache<K, V> {

    private final Map<K, V> vars;
    private final Map<K, Integer> counts;
    private final Map<Integer, LinkedHashSet<K>> lists;
    private final int capacity;
    private int min = -1;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.vars = new HashMap<>();
        this.counts = new HashMap<>();
        this.lists = new HashMap<>();
        this.lists.put(1, new LinkedHashSet<>());
    }

    public LFUCache() {
        this.capacity = 10;
        this.vars = new HashMap<>();
        this.counts = new HashMap<>();
        this.lists = new HashMap<>();
        this.lists.put(1, new LinkedHashSet<>());
    }

    @Override
    public V get(K key) {
        if (!vars.containsKey(key))
            return null;

        int count = counts.get(key);
        counts.put(key, count + 1);

        lists.get(count).remove(key);

        if (count == min && lists.get(count).isEmpty())
            min++;

        lists.computeIfAbsent(count + 1, k -> new LinkedHashSet<>()).add(key);

        return vars.get(key);
    }

    @Override
    public void set(K key, V value) {
        if (capacity <= 0)
            return;

        if (vars.containsKey(key)) {
            vars.put(key, value);
            get(key);
            return;
        }

        if (vars.size() >= capacity) {
            K evictKey = lists.get(min).iterator().next();
            lists.get(min).remove(evictKey);
            vars.remove(evictKey);
            counts.remove(evictKey);
        }

        vars.put(key, value);
        counts.put(key, 1);
        min = 1;
        lists.computeIfAbsent(1, k -> new LinkedHashSet<>()).add(key);
    }

    @Override
    public void remove(K key) {
        vars.remove(key);
        counts.remove(key);
        for (Map.Entry<Integer, LinkedHashSet<K>> entry : lists.entrySet()) {
            entry.getValue().remove(key);
        }
    }
}
