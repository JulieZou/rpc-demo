package com.ibicd.rpc.rpc.protocol.rpc;

import com.ibicd.rpc.common.serialize.Serialization;
import com.ibicd.rpc.remoting.Client;
import com.ibicd.rpc.rpc.Invoker;
import com.ibicd.rpc.rpc.Response;
import com.ibicd.rpc.rpc.RpcInvocation;
import com.ibicd.rpc.rpc.protocol.rpc.handler.RpcClientHandler;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName RpcClientInvoker
 * @Description TODO
 * @Author Julie
 * @Date 2021/4/1 7:47
 * @Version 1.0
 */
public class RpcClientInvoker implements Invoker {

    private Client client;
    private Serialization serialization;

    public RpcClientInvoker(Client client, Serialization serialization) {
        this.client = client;
        this.serialization = serialization;
    }

    @Override
    public Class<?> getInterface() {
        return null;
    }

    @Override
    public Object invoke(RpcInvocation invocation) throws Exception {
        //1. 序列化数据
        byte[] requestBody = this.serialization.serialize(invocation);

        //2. 发起请求
        this.client.getChannel().send(requestBody);

        CompletableFuture completableFuture = RpcClientHandler.waitResult(invocation.getId());
        Response response = (Response) completableFuture.get(60, TimeUnit.SECONDS);
        if (response.getStatus() == 200) {
            return response.getContent();
        } else {
            throw new Exception("server error..." + response.getContent().toString());
        }
    }
}
