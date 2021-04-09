package com.ibicd.rpc.config;

/**
 * @ClassName RegistryConfig
 * @Description TODO
 * @Author Julie
 * @Date 2021/4/1 8:00
 * @Version 1.0
 */
public class RegistryConfig {

    private String address;

    private String userName;

    private String password;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
