package com.ibicd.rpc.remoting.netty;

import com.ibicd.rpc.remoting.Codec;
import com.ibicd.rpc.remoting.Handler;
import com.ibicd.rpc.remoting.Server;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;
import java.net.URI;

/**
 * @ClassName NettyServer
 * @Description TODO
 * @Author Julie
 * @Date 2021/3/31 8:22
 * @Version 1.0
 */
public class NettyServer implements Server {
    EventLoopGroup boss = new NioEventLoopGroup();
    EventLoopGroup worker = new NioEventLoopGroup();

    @Override
    public void start(URI uri, Codec codec, Handler handler) {

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(uri.getHost(), uri.getPort()))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyCodec(codec.createInstance()));
                            ch.pipeline().addLast(new NettyHandler(handler));
                        }
                    });
            ChannelFuture channelFuture = bootstrap.bind().sync();
            System.out.println("完成端口绑定和服务器启动");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
