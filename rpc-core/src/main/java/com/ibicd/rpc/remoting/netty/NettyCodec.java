package com.ibicd.rpc.remoting.netty;

import com.ibicd.rpc.remoting.Codec;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

import java.util.List;

/**
 * @ClassName NettyCodec
 * @Description TODO
 * @Author Julie
 * @Date 2021/4/1 7:10
 * @Version 1.0
 */
public class NettyCodec extends ChannelDuplexHandler {

    private Codec codec;

    public NettyCodec(Codec codec) {
        this.codec = codec;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf data = (ByteBuf) msg;
        byte[] dataBytes = new byte[data.readableBytes()];
        List<Object> out = codec.decode(dataBytes);

        for (Object o : out) {
            ctx.fireChannelRead(o);
        }

        System.out.println("NettyCodec 内容为：" + msg);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        byte[] encode = codec.encode(msg);
        super.write(ctx, Unpooled.wrappedBuffer(encode), promise);
    }


}
