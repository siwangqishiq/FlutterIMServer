package xyz.panyi.imserver.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *  LRU实现的缓存
 *
 *  基于LinkedHashMap
 * @param <K>
 * @param <V>
 */
public class LruCache<K, V> extends LinkedHashMap<K, V> {
    private int cacheSize;

    public LruCache(int maxSize){
        super(16, 0.75f, true);
        this. cacheSize = maxSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() >= cacheSize;
    }
}//end class
