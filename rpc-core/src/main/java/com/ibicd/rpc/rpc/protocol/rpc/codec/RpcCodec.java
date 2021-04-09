package com.ibicd.rpc.rpc.protocol.rpc.codec;

import com.ibicd.rpc.common.serialize.Serialization;
import com.ibicd.rpc.common.tools.ByteUtil;
import com.ibicd.rpc.remoting.Codec;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName RpcCodec
 * @Description TODO
 * @Author Julie
 * @Date 2021/4/1 7:45
 * @Version 1.0
 */
public class RpcCodec implements Codec {

    public final static byte[] MAGIC = new byte[]{(byte) 0xda, (byte) 0xbb};
    //协议头部长度
    public final int HEAD_LEN = 6;
    // 用来临时保留没有处理过的请求报文
    ByteBuf tempMsg = Unpooled.buffer();

    /**
     * 服务端 --编码--Response
     * 客户端-- 编码-- RPCInvocation
     *
     * @param msg
     * @return
     * @throws Exception
     */
    @Override
    public byte[] encode(Object msg) throws Exception {
        byte[] responseBody = (byte[]) msg;
        ByteBuf requestBuffer = Unpooled.buffer();
        requestBuffer.writeBytes(MAGIC);
        requestBuffer.writeBytes(ByteUtil.int2bytes(responseBody.length));
        requestBuffer.writeBytes(responseBody);

        byte[] result = new byte[requestBuffer.readableBytes()];
        requestBuffer.readBytes(result);
        return result;
    }

    /**
     * 解码
     * 客户端 -- Response
     * 服务端-- RpcInvocation
     * 考虑粘包与拆包的问题
     *
     * @param data
     * @return
     * @throws Exception
     */
    @Override
    public List<Object> decode(byte[] data) throws Exception {
        List<Object> results = new ArrayList<>();
        ByteBuf message = Unpooled.buffer();
        int tempMsgSize = tempMsg.readableBytes();
        // 如果有上一次的暂存报文就合并
        if (tempMsgSize > 0) {
            message.writeBytes(tempMsg);
            message.writeBytes(data);
        } else {
            message.writeBytes(data);
        }

        for (; ; ) {
            if (HEAD_LEN >= message.readableBytes()) {
                tempMsg.clear();
                tempMsg.writeBytes(message);
                return results;
            }

            //读取2个字节到magic中判断是否有关键字
            byte[] magic = new byte[2];
            message.readBytes(magic);
            for (; ; ) {
                if (magic[0] != MAGIC[0] || magic[1] != MAGIC[1]) {
                    //所有数据都读取完了，也没有发现有正确的头部
                    if (message.readableBytes() == 0) {
                        tempMsg.clear();
                        tempMsg.writeByte(magic[1]);
                        return results;
                    }
                    magic[0] = magic[1];
                    magic[1] = message.readByte();
                } else {
                    break;
                }
            }

            byte[] lengthBytes = new byte[4];
            message.readBytes(lengthBytes);
            int length = ByteUtil.Bytes2Int_BE(lengthBytes);
            //如果body没有传输完，则先不处理
            if (message.readableBytes() < length) {
                tempMsg.clear();
                tempMsg.writeBytes(magic);
                tempMsg.writeBytes(lengthBytes);
                tempMsg.writeBytes(message);
                return results;
            }

            byte[] body = new byte[length];
            message.readBytes(body);
            Object deserialize = getSerialization().deserialize(body, decodeType);
            results.add(deserialize);
        }

    }

    @Override
    public Codec createInstance() {

        RpcCodec codec = new RpcCodec();
        codec.setSerialization(this.serialization);
        codec.setDecodeType(this.decodeType);
        return codec;
    }

    Serialization serialization;
    Class decodeType;

    public Serialization getSerialization() {
        return serialization;
    }

    public void setSerialization(Serialization serialization) {
        this.serialization = serialization;
    }

    public Class getDecodeType() {
        return decodeType;
    }

    public void setDecodeType(Class decodeType) {
        this.decodeType = decodeType;
    }
}
