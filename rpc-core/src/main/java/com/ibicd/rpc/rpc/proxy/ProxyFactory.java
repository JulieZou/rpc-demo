package com.ibicd.rpc.rpc.proxy;

import com.ibicd.rpc.rpc.Invoker;
import com.ibicd.rpc.rpc.RpcInvocation;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @ClassName ProxyFactory
 * @Description TODO
 * @Author Julie
 * @Date 2021/4/1 7:42
 * @Version 1.0
 */
public class ProxyFactory {

    public static Object getProxy(Invoker invoker, Class<?>[] interfaces) {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                interfaces, new InvokerInvocationHandler(invoker));

    }

    public static Invoker getInvoker(Object proxy, Class type) {
        return new Invoker() {
            @Override
            public Class<?> getInterface() {
                return type;
            }

            @Override
            public Object invoke(RpcInvocation invocation) throws Exception {
                Method method = proxy.getClass().getMethod(invocation.getMethodName(), invocation.getParameterTypes());

                return method.invoke(proxy, invocation.getArguments());
            }
        };

    }
}
