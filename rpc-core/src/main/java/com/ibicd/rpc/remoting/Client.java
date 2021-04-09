package com.ibicd.rpc.remoting;

import java.net.URI;

/**
 * @ClassName Client
 * @Description TODO
 * @Author Julie
 * @Date 2021/4/8 22:14
 * @Version 1.0
 */
public interface Client {

    void connect(URI uri, Codec codec, Handler handler);

    RpcChannel getChannel();
}
