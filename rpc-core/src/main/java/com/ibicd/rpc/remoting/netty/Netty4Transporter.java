package com.ibicd.rpc.remoting.netty;

import com.ibicd.rpc.remoting.*;

import java.net.URI;

/**
 * @ClassName NettyTransporter
 * @Description TODO
 * @Author Julie
 * @Date 2021/3/31 8:21
 * @Version 1.0
 */
public class Netty4Transporter implements Transporter {

    @Override
    public Server start(URI uri, Codec codec, Handler handler) {
        NettyServer server = new NettyServer();
        server.start(uri, codec, handler);
        return server;
    }

    @Override
    public Client connect(URI uri, Codec codec, Handler handler) {
        NettyClient client = new NettyClient();
        client.connect(uri, codec, handler);
        return client;
    }
}
