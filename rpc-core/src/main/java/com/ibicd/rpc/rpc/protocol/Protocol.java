package com.ibicd.rpc.rpc.protocol;

import com.ibicd.rpc.rpc.Invoker;

import java.net.URI;

/**
 * @ClassName Protocol
 * @Description TODO
 * @Author Julie
 * @Date 2021/4/1 7:42
 * @Version 1.0
 */
public interface Protocol {

    /**
     * 开放服务
     *
     * @param exportUri
     * @param invoker 调用具体实现类的代理对象
     */
    public void export(URI exportUri, Invoker invoker);

    /**
     * 获取一个协议对应的Invoker，用于调用
     *
     * @param consumerUri
     * @return
     */
    public Invoker refer(URI consumerUri);
}
