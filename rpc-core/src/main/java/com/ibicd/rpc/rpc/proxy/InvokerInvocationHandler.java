package com.ibicd.rpc.rpc.proxy;

import com.ibicd.rpc.rpc.Invoker;
import com.ibicd.rpc.rpc.RpcInvocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @ClassName InvokerInvocationHandler
 * @Description TODO
 * @Author Julie
 * @Date 2021/4/1 7:42
 * @Version 1.0
 */
public class InvokerInvocationHandler implements InvocationHandler {
    private final Invoker invoker;

    public InvokerInvocationHandler(Invoker invoker) {
        this.invoker = invoker;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getDeclaringClass() == Object.class) {
            return method.invoke(invoker, args);
        }
        String methodName = method.getName();
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length == 0) {
            if (methodName.equals("toString")) {
                return invoker.toString();
            } else if ("$destroy".equals(methodName)) {
                return null;
            } else if ("hashCode".equals(methodName)) {
                return invoker.hashCode();
            }
        } else if (parameterTypes.length == 1 && "equals".equals(methodName)) {
            return invoker.equals(args[0]);
        }

        RpcInvocation invocation = new RpcInvocation();
        invocation.setMethodName(methodName);
        invocation.setArguments(args);
        invocation.setParameterTypes(parameterTypes);
        invocation.setServiceName(method.getDeclaringClass().getName());
        return invoker.invoke(invocation);
    }
}
