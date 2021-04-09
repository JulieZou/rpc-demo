package com.ibicd.rpc.remoting;

/**
 * @ClassName Handler
 * @Description 协议Handler
 * @Author Julie
 * @Date 2021/4/1 6:56
 * @Version 1.0
 */
public interface Handler {

    void onReceive(RpcChannel channel, Object message) throws Exception;

    void onWrite(RpcChannel channel, Object message) throws Exception;
}
