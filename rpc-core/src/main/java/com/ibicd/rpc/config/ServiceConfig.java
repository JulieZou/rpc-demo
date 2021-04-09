package com.ibicd.rpc.config;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ServiceConfig
 * @Description TODO
 * @Author Julie
 * @Date 2021/4/1 8:00
 * @Version 1.0
 */
public class ServiceConfig {

    private List<RegistryConfig> registryConfigs;

    private List<ProtocolConfig> protocolConfigs;

    //具体的接口
    private Class service;

    // 具体的实现类
    private Object reference;

    private String version;

    public synchronized void addRegistryConfig(RegistryConfig registryConfig) {
        if (registryConfigs == null) {
            registryConfigs = new ArrayList<RegistryConfig>();
        }
        this.registryConfigs.add(registryConfig);
    }

    public synchronized void addProtocolConfig(ProtocolConfig protocolConfig) {
        if (protocolConfigs == null) {
            this.protocolConfigs = new ArrayList<ProtocolConfig>();
        }
        this.protocolConfigs.add(protocolConfig);
    }

    public List<RegistryConfig> getRegistryConfigs() {
        return registryConfigs;
    }

    public void setRegistryConfigs(List<RegistryConfig> registryConfigs) {
        this.registryConfigs = registryConfigs;
    }

    public List<ProtocolConfig> getProtocolConfigs() {
        return protocolConfigs;
    }

    public void setProtocolConfigs(List<ProtocolConfig> protocolConfigs) {
        this.protocolConfigs = protocolConfigs;
    }

    public Class getService() {
        return service;
    }

    public void setService(Class service) {
        this.service = service;
    }

    public Object getReference() {
        return reference;
    }

    public void setReference(Object reference) {
        this.reference = reference;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
