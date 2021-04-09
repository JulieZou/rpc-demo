package com.ibicd.rpc.rpc.cluster;

import com.ibicd.rpc.common.tools.SpiUtils;
import com.ibicd.rpc.config.ReferenceConfig;
import com.ibicd.rpc.config.RegistryConfig;
import com.ibicd.rpc.registry.NotifyListener;
import com.ibicd.rpc.registry.RegistryService;
import com.ibicd.rpc.rpc.Invoker;
import com.ibicd.rpc.rpc.RpcInvocation;
import com.ibicd.rpc.rpc.protocol.Protocol;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName ClusterInvoker
 * @Description 集群环境下的Invoker -- 包含一个服务下的多个实例调用
 * @Author Julie
 * @Date 2021/4/1 7:49
 * @Version 1.0
 */
public class ClusterInvoker implements Invoker {

    ReferenceConfig referenceConfig;

    // 代表这个服务能够调用的所有实例
    Map<URI, Invoker> invokers = new ConcurrentHashMap<>();
    LoadBalance loadBalance;

    public ClusterInvoker(ReferenceConfig referenceConfig) throws URISyntaxException {
        this.referenceConfig = referenceConfig;
        String loadBalanceName = referenceConfig.getLoadBalance();
        this.loadBalance = (LoadBalance) SpiUtils.getServiceImpl(loadBalanceName, LoadBalance.class);

        //接口的全类名
        String serviceName = referenceConfig.getService().getName();
        for (RegistryConfig registryConfig : referenceConfig.getRegistryConfigs()) {
            URI registUri = new URI(registryConfig.getAddress());
            RegistryService registryService = (RegistryService) SpiUtils.getServiceImpl(registUri.getScheme(), RegistryService.class);
            registryService.init(registUri);

            registryService.subscribe(serviceName, new NotifyListener() {

                @Override
                public void notify(Set<URI> uris) {
                    //uris 为当前最新的服务实例
                    System.out.println("更新前的服务invoker 信息：" + invokers);
                    for (URI uri : invokers.keySet()) {
                        if (!uris.contains(uri)) {
                            invokers.remove(uri);
                        }
                    }

                    for (URI uri : uris) {
                        if (!invokers.containsKey(uri)) {
                            Protocol protocol = (Protocol) SpiUtils.getServiceImpl(uri.getScheme(), Protocol.class);
                            Invoker invoker = protocol.refer(uri);
                            invokers.putIfAbsent(uri, invoker);
                        }
                    }
                    System.out.println("更新后的服务invoker 信息：" + invokers);
                }
            });
        }
    }

    @Override
    public Class getInterface() {

        return referenceConfig.getService();
    }

    @Override
    public Object invoke(RpcInvocation invocation) throws Exception {
        Invoker select = loadBalance.select(invokers);
        return select.invoke(invocation);
    }
}
