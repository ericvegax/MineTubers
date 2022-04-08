package com.github.realericvega.minetubers.manager.data.redis;

import redis.clients.jedis.Jedis;

public final class RedisManager {

    private final Jedis JEDIS;

    private RedisManager(Jedis jedis) {
        this.JEDIS = jedis;
    }

    public static RedisManager of(Jedis jedis) {
        return new RedisManager(jedis);
    }

    public <T> void set(T key, T value) {

    }

    public <T> T get(T key) {
        return key;
    }
}
