package com.ibicd.rpc.registry;

import java.net.URI;

/**
 * @ClassName RegistryService
 * @Description 注册服务
 * @Author Julie
 * @Date 2021/4/8 21:33
 * @Version 1.0
 */
public interface RegistryService {

    void init(URI address);

    void regist(URI uri);

    void subscribe(String service, NotifyListener notifyListener);
}
