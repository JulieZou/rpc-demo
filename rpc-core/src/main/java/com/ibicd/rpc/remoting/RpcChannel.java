package com.ibicd.rpc.remoting;

/**
 * @ClassName RpcChannel
 * @Description 表示一个客户端的连接，不同的底层网络框架，这个连接的定义不同，所以为了统一。
 * @Author Julie
 * @Date 2021/4/1 7:06
 * @Version 1.0
 */
public interface RpcChannel {

    void send(byte[] message);
}
