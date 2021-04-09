package com.ibicd.rpc.config;

/**
 * @ClassName ProtocolConfig
 * @Description 协议相关配置
 * @Author Julie
 * @Date 2021/4/1 8:00
 * @Version 1.0
 */
public class ProtocolConfig {

    private String name;

    private String port;

    private String host;

    private String serialization;

    private String transporter;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getSerialization() {
        return serialization;
    }

    public void setSerialization(String serialization) {
        this.serialization = serialization;
    }

    public String getTransporter() {
        return transporter;
    }

    public void setTransporter(String transporter) {
        this.transporter = transporter;
    }
}
