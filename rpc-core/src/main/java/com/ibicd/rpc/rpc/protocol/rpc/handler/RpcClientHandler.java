package com.ibicd.rpc.rpc.protocol.rpc.handler;

import com.ibicd.rpc.remoting.Handler;
import com.ibicd.rpc.remoting.RpcChannel;
import com.ibicd.rpc.rpc.Response;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName RpcClientHandler
 * @Description TODO
 * @Author Julie
 * @Date 2021/4/1 7:46
 * @Version 1.0
 */
public class RpcClientHandler implements Handler {

    private static Map<Long, CompletableFuture> invokerMap = new ConcurrentHashMap<>();

    public static CompletableFuture waitResult(Long messageId) {
        CompletableFuture completableFuture = new CompletableFuture();
        invokerMap.put(messageId, completableFuture);
        return completableFuture;
    }

    @Override
    public void onReceive(RpcChannel channel, Object message) throws Exception {
        Response response = (Response) message;
        invokerMap.get(response.getRequestId()).complete(response);
        invokerMap.remove(response.getRequestId());

    }

    @Override
    public void onWrite(RpcChannel channel, Object message) throws Exception {

    }
}
