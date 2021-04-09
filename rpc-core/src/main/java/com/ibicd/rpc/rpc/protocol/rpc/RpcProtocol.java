package com.ibicd.rpc.rpc.protocol.rpc;

import com.ibicd.rpc.common.serialize.Serialization;
import com.ibicd.rpc.common.serialize.json.JsonSerialization;
import com.ibicd.rpc.common.tools.SpiUtils;
import com.ibicd.rpc.common.tools.URIUtils;
import com.ibicd.rpc.remoting.Client;
import com.ibicd.rpc.remoting.Transporter;
import com.ibicd.rpc.rpc.Invoker;
import com.ibicd.rpc.rpc.Response;
import com.ibicd.rpc.rpc.RpcInvocation;
import com.ibicd.rpc.rpc.protocol.Protocol;
import com.ibicd.rpc.rpc.protocol.rpc.codec.RpcCodec;
import com.ibicd.rpc.rpc.protocol.rpc.handler.RpcClientHandler;
import com.ibicd.rpc.rpc.protocol.rpc.handler.RpcServerHandler;

import java.net.URI;

/**
 * @ClassName RpcProtocol
 * @Description RPC 协议实现服务
 * @Author Julie
 * @Date 2021/4/1 7:47
 * @Version 1.0
 */
public class RpcProtocol implements Protocol {

    @Override
    public void export(URI exportUri, Invoker invoker) {
        //1. 编解码器
        RpcCodec rpcCodec = new RpcCodec();
        rpcCodec.setDecodeType(RpcInvocation.class);
        rpcCodec.setSerialization(new JsonSerialization());

        //2. 收到请求处理器
        RpcServerHandler serverHandler = new RpcServerHandler();
        serverHandler.setInvoker(invoker);

        //3.底层网络框架
        String transporterName = URIUtils.getParam(exportUri, "transporter");
        Transporter transporter = (Transporter) SpiUtils.getServiceImpl(transporterName, Transporter.class);

        //4. 启动服务
        transporter.start(exportUri, rpcCodec, serverHandler);
    }

    /**
     * 获取已经注册的服务
     *
     * @param consumerUri
     * @return
     */
    @Override
    public Invoker refer(URI consumerUri) {
        String serializationName = URIUtils.getParam(consumerUri, "serialization");
        Serialization serialization = (Serialization) SpiUtils.getServiceImpl(serializationName, Serialization.class);

        RpcCodec codec = new RpcCodec();
        codec.setSerialization(serialization);
        codec.setDecodeType(Response.class);

        RpcClientHandler clientHandler = new RpcClientHandler();
        String transporterName = URIUtils.getParam(consumerUri, "transporter");
        Transporter transporter = (Transporter) SpiUtils.getServiceImpl(transporterName, Transporter.class);
        //获取一个客户端连接
        Client connect = transporter.connect(consumerUri, codec, clientHandler);

        RpcClientInvoker clientInvoker = new RpcClientInvoker(connect, serialization);
        return clientInvoker;
    }
}
