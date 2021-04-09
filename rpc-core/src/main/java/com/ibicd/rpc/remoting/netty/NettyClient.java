package com.ibicd.rpc.remoting.netty;

import com.ibicd.rpc.remoting.Client;
import com.ibicd.rpc.remoting.Codec;
import com.ibicd.rpc.remoting.Handler;
import com.ibicd.rpc.remoting.RpcChannel;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.URI;

/**
 * @ClassName NettyClient
 * @Description TODO
 * @Author Julie
 * @Date 2021/4/8 22:15
 * @Version 1.0
 */
public class NettyClient implements Client {
    RpcChannel rpcChannel = null;
    EventLoopGroup group = null;

    @Override
    public void connect(URI uri, Codec codec, Handler handler) {
        try {
            group = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast(new NettyCodec(codec));
                            ch.pipeline().addLast(new NettyHandler(handler));
                        }
                    });

            //创建同步连接
            ChannelFuture channelFuture = bootstrap.connect(uri.getHost(), uri.getPort()).sync();
            rpcChannel = new NettyChannel(channelFuture.channel());

            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("我要停机了...");
                        synchronized (NettyServer.class) {
                            group.shutdownGracefully().sync();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public RpcChannel getChannel() {
        return this.rpcChannel;
    }
}
