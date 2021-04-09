package com.ibicd.rpc.remoting;

import java.net.URI;

/**
 * @ClassName Transporter
 * @Description 底层网络传输-统一入口【客户端，服务端】
 * @Author Julie
 * @Date 2021/3/31 8:20
 * @Version 1.0
 */
public interface Transporter {


    Server start(URI uri, Codec codec, final Handler handler);

    /**
     * 获取客户端连接
     *
     * @param uri
     * @param codec
     * @param handler
     * @return
     */
    Client connect(URI uri, Codec codec, Handler handler);
}
