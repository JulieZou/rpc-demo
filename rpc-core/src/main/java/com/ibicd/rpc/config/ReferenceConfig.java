package com.ibicd.rpc.config;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ReferenceConfig
 * @Description TODO
 * @Author Julie
 * @Date 2021/4/1 8:00
 * @Version 1.0
 */
public class ReferenceConfig {

    private List<RegistryConfig> registryConfigs;

    private List<ProtocolConfig> protocolConfigs;

    private Class service;

    private String version;

    private String loadBalance;

    public synchronized void addRegistryConfig(RegistryConfig registryConfig) {
        if (this.registryConfigs == null) {
            this.registryConfigs = new ArrayList<RegistryConfig>();
        }
        this.registryConfigs.add(registryConfig);
    }

    public synchronized void addProtocolConfig(ProtocolConfig protocolConfig) {
        if (this.protocolConfigs == null) {
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLoadBalance() {
        return loadBalance;
    }

    public void setLoadBalance(String loadBalance) {
        this.loadBalance = loadBalance;
    }
}
