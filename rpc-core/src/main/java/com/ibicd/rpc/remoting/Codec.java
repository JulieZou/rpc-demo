package com.ibicd.rpc.remoting;

import java.util.List;

/**
 * @ClassName Codec
 * @Description TODO
 * @Author Julie
 * @Date 2021/4/1 6:54
 * @Version 1.0
 */
public interface Codec {

    byte[] encode(Object msg) throws Exception;

    List<Object> decode(byte[] message) throws Exception;

    Codec createInstance();

}
