package com.ibicd.rpc.registry.redis;

import com.ibicd.rpc.common.tools.URIUtils;
import com.ibicd.rpc.registry.NotifyListener;
import com.ibicd.rpc.registry.RegistryService;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName RedisRegistry
 * @Description Redis 注册服务
 * @Author Julie
 * @Date 2021/4/8 21:34
 * @Version 1.0
 */
public class RedisRegistry implements RegistryService {
    public static final int TIME_OUT = 15;

    URI address;

    ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(5);

    ArrayList<URI> servicesHeartBeat = new ArrayList<>();
    JedisPubSub jedisPubSub;
    //服务与服务对应的实例
    Map<String, Set<URI>> localCache = new ConcurrentHashMap<>();
    // 服务与服务对应的监听器
    Map<String, NotifyListener> listenerMap = new ConcurrentHashMap<>();

    @Override
    public void init(URI address) {
        this.address = address;

        executorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                Jedis jedis = new Jedis(address.getHost(), address.getPort());
                for (URI uri : servicesHeartBeat) {
                    String key = "rpc-" + uri.toString();
                    jedis.expire(key, TIME_OUT);//设置每个key 的过期时间为15s
                }
                jedis.close();
            }
            // 初始延迟3s执行，每5s 执行一次
        }, 3000, 5000, TimeUnit.MILLISECONDS);

        executorService.execute(new Runnable() {
            @Override
            public void run() {

                jedisPubSub = new JedisPubSub() {
                    @Override
                    public void onPSubscribe(String pattern, int subscribedChannels) {
                        System.out.println("注冊中心开始监听: " + pattern);
                    }

                    @Override
                    public void onPMessage(String pattern, String channel, String message) {
                        try {
                            URI serviceUri = new URI(channel.replace("__keyspace@0__:rpc-", ""));
                            String service = URIUtils.getService(serviceUri);
                            if ("set".equals(message)) {
                                Set<URI> uris = localCache.get(service);
                                if (uris != null) {
                                    uris.add(serviceUri);
                                }
                            }

                            if ("expired".equals(message)) {
                                Set<URI> uris = localCache.get(service);
                                if (uris != null) {
                                    uris.remove(serviceUri);
                                }
                            }
                            if ("set".equals(message) || "expired".equals(message)) {
                                System.out.println("服务实例有变化，开始刷新");
                                NotifyListener notifyListener = listenerMap.get(service);
                                if (notifyListener != null) {
                                    notifyListener.notify(localCache.get(service));
                                }
                            }
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                    }
                };

                Jedis jedis = new Jedis(address.getHost(), address.getPort());
                jedis.psubscribe(jedisPubSub, "__keyspace@0__:rpc-*");
            }
        });

    }


    // trpc-TrpcProtocol://127.0.0.1:10088/com.study.dubbo.sms.api.SmsService?transporter=Netty4Transporter&serialization=JsonSerialization
    @Override
    public void regist(URI uri) {
        String key = "rpc-" + uri.toString();
        Jedis jedis = new Jedis(address.getHost(), address.getPort());
        jedis.setex(key, TIME_OUT, String.valueOf(System.currentTimeMillis()));
        jedis.close();
        servicesHeartBeat.add(uri);
    }

    @Override
    public void subscribe(String service, NotifyListener notifyListener) {
        try {
            if (localCache.get(service) == null) {
                localCache.putIfAbsent(service, new HashSet<>());
                listenerMap.putIfAbsent(service, notifyListener);
                Jedis jedis = new Jedis(address.getHost(), address.getPort());
                String key = "rpc-" + service;
                Set<String> serviceInstances = jedis.keys("rpc-*" + service + "?*");
                for (String serviceInstance : serviceInstances) {
                    URI uri = new URI(serviceInstance.replace("rpc-", ""));
                    localCache.get(service).add(uri);
                }
                notifyListener.notify(localCache.get(service));
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
