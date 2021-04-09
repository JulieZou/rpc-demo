package com.ibicd.rpc.rpc;

/**
 * @ClassName Invoker
 * @Description 具体的调用
 * @Author Julie
 * @Date 2021/4/1 7:25
 * @Version 1.0
 */
public interface Invoker {

    Class getInterface();

    Object invoke(RpcInvocation invocation) throws NoSuchMethodException, Exception;

}
