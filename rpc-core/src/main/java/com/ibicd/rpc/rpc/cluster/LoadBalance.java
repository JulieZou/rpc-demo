package com.ibicd.rpc.rpc.cluster;

import com.ibicd.rpc.rpc.Invoker;

import java.net.URI;
import java.util.Map;

/**
 * @ClassName LoadBalance
 * @Description 负载均衡器
 * @Author Julie
 * @Date 2021/4/1 7:50
 * @Version 1.0
 */
public interface LoadBalance {

    /**
     * 传入集合，选择其中一个
     *
     * @param invokerMap
     * @return
     */
    Invoker select(Map<URI, Invoker> invokerMap);
}
