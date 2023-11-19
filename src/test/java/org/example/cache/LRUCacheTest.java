package org.example.cache;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LRUCacheTest {

    @Test
    void LRUCacheGetWithValidKey() {
        LRUCache<String, Integer> cache = new LRUCache<>(3);

        cache.set("A", 1);
        cache.set("B", 2);

        assertEquals(1, cache.get("A"));
        assertEquals(2, cache.get("B"));
        assertNull(cache.get("C"));
    }

    @Test
    void LRUCacheSetAndEviction() {
        LRUCache<String, Integer> cache = new LRUCache<>(3);

        cache.set("A", 1);
        cache.set("B", 2);
        cache.set("C", 3);

        assertEquals(1, cache.get("A"));
        assertEquals(2, cache.get("B"));
        assertEquals(3, cache.get("C"));


        cache.set("D", 4);

        assertNull(cache.get("A"));
        assertEquals(2, cache.get("B"));
        assertEquals(3, cache.get("C"));
        assertEquals(4, cache.get("D"));
    }

    @Test
    void LRUCacheRemove() {
        LRUCache<String, Integer> cache = new LRUCache<>(3);

        cache.set("A", 1);
        cache.set("B", 2);

        assertEquals(1, cache.get("A"));
        assertEquals(2, cache.get("B"));

        cache.remove("A");

        assertNull(cache.get("A"));
        assertEquals(2, cache.get("B"));
    }
}