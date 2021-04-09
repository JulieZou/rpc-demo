package com.ibicd.rpc.common.serialize;

/**
 * @ClassName Serialization
 * @Description TODO
 * @Author Julie
 * @Date 2021/3/30 23:57
 * @Version 1.0
 */
public interface Serialization {

    byte[] serialize(Object output) throws Exception;

    Object deserialize(byte[] input, Class clazz) throws Exception;
}
