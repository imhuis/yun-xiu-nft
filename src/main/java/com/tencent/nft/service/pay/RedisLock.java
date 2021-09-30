package com.tencent.nft.service.pay;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author: imhuis
 * @date: 2021/9/30
 * @description:
 */
public class RedisLock {

    public RedisLock(RedisTemplate<String, Object> redisTemplate, String key) {
    }

    public boolean tryLock() {
        return true;
    }

    public void unlock() {
    }
}
