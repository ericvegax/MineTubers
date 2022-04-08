package com.github.realericvega.minetubers.manager.data.redis;

import lombok.Getter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public final class RedisPool {

    @Getter
    private static Jedis jedis;

    private RedisPool(String host, int port) {
        JedisPool pool = new JedisPool(host, port);

        try (Jedis jedis = pool.getResource()) {
            RedisPool.jedis = jedis;
        }
    }

    public static RedisPool of(String host, int port) {
        return new RedisPool(host, port);
    }

    public void connectToJedisPool() {
        if (jedis != null) {
            jedis.connect();
        }
    }

    public void closeJedisPool() {
        if (jedis != null) {
            jedis.close();
        }
    }
}
