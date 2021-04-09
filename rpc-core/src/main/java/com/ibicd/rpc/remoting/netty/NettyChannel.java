package com.ibicd.rpc.remoting.netty;

import com.ibicd.rpc.remoting.RpcChannel;

import io.netty.channel.Channel;

/**
 * @ClassName NettyChannel
 * @Description TODO
 * @Author Julie
 * @Date 2021/4/1 7:11
 * @Version 1.0
 */
public class NettyChannel implements RpcChannel {

    private Channel channel;

    public NettyChannel(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void send(byte[] message) {
        channel.writeAndFlush(message);
    }
}
