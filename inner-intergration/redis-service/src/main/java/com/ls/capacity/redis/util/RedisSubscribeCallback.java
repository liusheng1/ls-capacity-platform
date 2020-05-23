package com.ls.capacity.redis.util;

 
public interface RedisSubscribeCallback {
    void callback(String msg);
}
