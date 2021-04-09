package com.ibicd.rpc.test.registry;

import com.ibicd.rpc.registry.NotifyListener;
import com.ibicd.rpc.registry.redis.RedisRegistry;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;

/**
 * @ClassName RedisRegistryTest
 * @Description TODO
 * @Author Julie
 * @Date 2021/4/9 18:46
 * @Version 1.0
 */
public class RedisRegistryTest {

    public static void main(String[] args) throws URISyntaxException {
        RedisRegistry redisRegistry = new RedisRegistry();
        redisRegistry.init(new URI("RedisRegistry://192.168.152.134:6379"));

        redisRegistry.subscribe("com.ibicd.api.SmsService", new NotifyListener() {
            @Override
            public void notify(Set<URI> uris) {
                System.out.println(" 输出为： "+uris);
            }
        });




    }
}
