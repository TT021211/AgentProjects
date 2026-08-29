package com.cuixing.wms.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
@Component
/* loaded from: RedisCache.class */
public class RedisCache {
    @Autowired
    public RedisTemplate redisTemplate;

    public <T> void setCacheObject(String key, T value) {
        this.redisTemplate.opsForValue().set(key, value);
    }

    public <T> void setCacheObject(String key, T value, Integer timeout, TimeUnit timeUnit) {
        this.redisTemplate.opsForValue().set(key, value, timeout.intValue(), timeUnit);
    }

    public boolean expire(String key, long timeout) {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    public boolean expire(String key, long timeout, TimeUnit unit) {
        return this.redisTemplate.expire(key, timeout, unit).booleanValue();
    }

    public <T> T getCacheObject(String key) {
        ValueOperations<String, T> operation = this.redisTemplate.opsForValue();
        return (T) operation.get(key);
    }

    public boolean deleteObject(String key) {
        return this.redisTemplate.delete(key).booleanValue();
    }

    public long deleteObject(Collection collection) {
        return this.redisTemplate.delete(collection).longValue();
    }

    public <T> long setCacheList(String key, List<T> dataList) {
        Long count = this.redisTemplate.opsForList().rightPushAll(key, dataList);
        if (count == null) {
            return 0L;
        }
        return count.longValue();
    }

    public <T> List<T> getCacheList(String key) {
        return this.redisTemplate.opsForList().range(key, 0L, -1L);
    }

    public <T> BoundSetOperations<String, T> setCacheSet(String key, Set<T> dataSet) {
        BoundSetOperations<String, T> setOperation = this.redisTemplate.boundSetOps(key);
        Iterator<T> it = dataSet.iterator();
        while (it.hasNext()) {
            setOperation.add((T) new Object[]{it.next()});
        }
        return setOperation;
    }

    public <T> Set<T> getCacheSet(String key) {
        return this.redisTemplate.opsForSet().members(key);
    }

    public <T> void setCacheMap(String key, Map<String, T> dataMap) {
        if (dataMap != null) {
            this.redisTemplate.opsForHash().putAll(key, dataMap);
        }
    }

    public <T> Map<String, T> getCacheMap(String key) {
        return this.redisTemplate.opsForHash().entries(key);
    }

    public <T> void setCacheMapValue(String key, String hKey, T value) {
        this.redisTemplate.opsForHash().put(key, hKey, value);
    }

    public <T> T getCacheMapValue(String key, String hKey) {
        HashOperations<String, String, T> opsForHash = this.redisTemplate.opsForHash();
        return (T) opsForHash.get(key, hKey);
    }

    public void delCacheMapValue(String key, String hKey) {
        HashOperations hashOperations = this.redisTemplate.opsForHash();
        hashOperations.delete(key, new Object[]{hKey});
    }

    public <T> List<T> getMultiCacheMapValue(String key, Collection<Object> hKeys) {
        return this.redisTemplate.opsForHash().multiGet(key, hKeys);
    }

    public Collection<String> keys(String pattern) {
        return this.redisTemplate.keys(pattern);
    }
}
