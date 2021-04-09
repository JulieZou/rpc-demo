package com.ibicd.rpc.rpc.protocol.rpc.handler;

import com.ibicd.rpc.common.serialize.Serialization;
import com.ibicd.rpc.remoting.Handler;
import com.ibicd.rpc.remoting.RpcChannel;
import com.ibicd.rpc.rpc.Invoker;
import com.ibicd.rpc.rpc.Response;
import com.ibicd.rpc.rpc.RpcInvocation;

/**
 * @ClassName RpcServerHandler
 * @Description TODO
 * @Author Julie
 * @Date 2021/4/1 7:46
 * @Version 1.0
 */
public class RpcServerHandler implements Handler {
    Invoker invoker;

    Serialization serialization;

    public Serialization getSerialization() {
        return serialization;
    }

    public void setSerialization(Serialization serialization) {
        this.serialization = serialization;
    }

    public Invoker getInvoker() {
        return invoker;
    }

    public void setInvoker(Invoker invoker) {
        this.invoker = invoker;
    }

    @Override
    public void onReceive(RpcChannel channel, Object message) throws Exception {
        RpcInvocation invocation = (RpcInvocation) message;
        System.out.println("RpcServerHandler 收到Invocation 信息：" + invocation);

        Response response = new Response();
        try {
            Object invokeResult = getInvoker().invoke(invocation);
            response.setStatus(200);
            response.setContent(invokeResult);
            // 设置请求id，表示要返回给谁
            response.setRequestId(invocation.getId());
            System.out.println("服务端执行结果：" + invokeResult);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(99);
            response.setContent(e.getMessage());
        }
        // 通过netty 发送响应数据
        byte[] responseBody = getSerialization().serialize(response);
        channel.send(responseBody);
    }

    @Override
    public void onWrite(RpcChannel channel, Object message) throws Exception {

    }
}
