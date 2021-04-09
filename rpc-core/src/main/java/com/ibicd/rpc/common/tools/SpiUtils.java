package com.ibicd.rpc.common.tools;

import java.util.ServiceLoader;

/**
 * @ClassName SpiUtils
 * @Description TODO
 * @Author Julie
 * @Date 2021/3/30 23:58
 * @Version 1.0
 */
public class SpiUtils {

    public static Object getServiceImpl(String name, Class<?> serivceType) {
        ServiceLoader<?> serviceLoader = ServiceLoader.load(serivceType, Thread.currentThread().getContextClassLoader());
        for (Object o : serviceLoader) {

            if (name.equals(o.getClass().getSimpleName())) {
                return o;
            }
        }

        return null;
    }
}
