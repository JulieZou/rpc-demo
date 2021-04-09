package com.ibicd.rpc.rpc;

import java.io.Serializable;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @ClassName RpcInvocation
 * @Description 由客户端发起的调用
 * @Author Julie
 * @Date 2021/4/1 7:26
 * @Version 1.0
 */
public class RpcInvocation implements Serializable {

    private static final long serialVersionUID = -8479408531735610955L;

    static AtomicLong SEQ = new AtomicLong();
    private Long id;
    private String serviceName;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] arguments;

    public RpcInvocation() {
        this.setId(incrementAndGet());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes == null ? new Class<?>[0] : parameterTypes;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public void setArguments(Object[] arguments) {
        this.arguments = arguments == null ? new Object[0] : arguments;
    }

    public long incrementAndGet() {
        long current;
        long next;
        do {
            current = SEQ.get();
            next = current >= 2147483647 ? 0 : current + 1;
        } while (!SEQ.compareAndSet(current, next));

        return next;
    }

    @Override
    public String toString() {
        return "RpcInvocation{" +
                "id=" + id +
                ", serviceName='" + serviceName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", parameterTypes=" + Arrays.toString(parameterTypes) +
                ", arguments=" + Arrays.toString(arguments) +
                '}';
    }
}
