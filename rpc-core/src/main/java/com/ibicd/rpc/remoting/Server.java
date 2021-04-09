package com.ibicd.rpc.remoting;

import java.net.URI;

/**
 * @ClassName Server
 * @Description TODO
 * @Author Julie
 * @Date 2021/3/31 8:20
 * @Version 1.0
 */
public interface Server {

    void start(URI uri, Codec codec, Handler handler);
}
