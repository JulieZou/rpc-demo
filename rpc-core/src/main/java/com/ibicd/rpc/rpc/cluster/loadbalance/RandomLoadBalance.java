package com.ibicd.rpc.rpc.cluster.loadbalance;

import com.ibicd.rpc.rpc.Invoker;
import com.ibicd.rpc.rpc.cluster.LoadBalance;

import java.net.URI;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * @ClassName RandomLoadBalance
 * @Description 随机负载均衡器
 * @Author Julie
 * @Date 2021/4/1 7:51
 * @Version 1.0
 */
public class RandomLoadBalance implements LoadBalance {
    @Override
    public Invoker select(Map<URI, Invoker> invokerMap) {
        int index = new Random().nextInt(invokerMap.values().size());
        return invokerMap.values().toArray(new Invoker[]{})[index];
    }
}
