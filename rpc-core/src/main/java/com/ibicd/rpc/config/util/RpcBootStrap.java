package com.ibicd.rpc.config.util;

import com.ibicd.rpc.common.tools.SpiUtils;
import com.ibicd.rpc.config.ProtocolConfig;
import com.ibicd.rpc.config.ReferenceConfig;
import com.ibicd.rpc.config.RegistryConfig;
import com.ibicd.rpc.config.ServiceConfig;
import com.ibicd.rpc.registry.RegistryService;
import com.ibicd.rpc.rpc.Invoker;
import com.ibicd.rpc.rpc.cluster.ClusterInvoker;
import com.ibicd.rpc.rpc.protocol.Protocol;
import com.ibicd.rpc.rpc.proxy.ProxyFactory;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @ClassName RpcBootStrap
 * @Description TODO
 * @Author Julie
 * @Date 2021/4/1 7:58
 * @Version 1.0
 */
public class RpcBootStrap {

    /**
     * 暴露服务：协议:host:port/serviceName?参数
     *
     * @param serviceConfig
     * @throws SocketException
     */
    public static void export(ServiceConfig serviceConfig) throws SocketException, URISyntaxException {
        Invoker invoker = ProxyFactory.getInvoker(serviceConfig.getReference(), serviceConfig.getService());

        String serviceName = serviceConfig.getService().getName();
        for (ProtocolConfig protocolConfig : serviceConfig.getProtocolConfigs()) {
            StringBuilder builder = new StringBuilder();
            builder.append(protocolConfig.getName() + "://");

            String hostAddress = NetworkInterface.getNetworkInterfaces()
                    .nextElement().getInterfaceAddresses().get(0).getAddress().getHostAddress();
            builder.append(hostAddress + ":");
            builder.append(protocolConfig.getPort() + "/");
            builder.append(serviceName + "?");

            builder.append("transporter=" + protocolConfig.getTransporter());
            builder.append("&serialization=" + protocolConfig.getSerialization());

            URI exportUri = new URI(builder.toString());
            System.out.println("准备暴露服务：" + exportUri.toString());

            Protocol protocol = (Protocol) SpiUtils.getServiceImpl(protocolConfig.getName(), Protocol.class);
            assert protocol != null;
            protocol.export(exportUri, invoker);

            for (RegistryConfig registryConfig : serviceConfig.getRegistryConfigs()) {
                URI registUri = new URI(registryConfig.getAddress());
                RegistryService registryService = (RegistryService) SpiUtils.getServiceImpl(registUri.getScheme(), RegistryService.class);
                assert registryService != null;
                registryService.init(registUri);
                registryService.regist(exportUri);
            }
        }
    }

    /**
     * 获取服务对应的引用（在具体需要使用的地方调用）
     *
     * @param referenceConfig
     * @return
     */
    public static Object getReferenceBean(ReferenceConfig referenceConfig) {
        try {
            ClusterInvoker invoker = new ClusterInvoker(referenceConfig);
            return ProxyFactory.getProxy(invoker, new Class[]{referenceConfig.getService()});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
