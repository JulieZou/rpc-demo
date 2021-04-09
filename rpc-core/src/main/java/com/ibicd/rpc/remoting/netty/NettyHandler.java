package com.ibicd.rpc.remoting.netty;

import com.ibicd.rpc.remoting.Handler;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;

/**
 * @ClassName NettyHandler
 * @Description TODO
 * @Author Julie
 * @Date 2021/3/31 8:22
 * @Version 1.0
 */
public class NettyHandler extends ChannelDuplexHandler {

    private Handler handler;

    public NettyHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        handler.onReceive(new NettyChannel(ctx.channel()), msg);
    }
}
