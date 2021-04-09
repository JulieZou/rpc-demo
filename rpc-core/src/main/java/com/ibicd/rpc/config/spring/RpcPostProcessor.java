package com.ibicd.rpc.config.spring;

import com.ibicd.rpc.config.ProtocolConfig;
import com.ibicd.rpc.config.ReferenceConfig;
import com.ibicd.rpc.config.RegistryConfig;
import com.ibicd.rpc.config.ServiceConfig;
import com.ibicd.rpc.config.annotation.TRpcReferencre;
import com.ibicd.rpc.config.annotation.TRpcService;
import com.ibicd.rpc.config.util.RpcBootStrap;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Field;

/**
 * @ClassName RpcProcessor
 * @Description Bean的后置处理器（主要暴露服务与设置服务的引用）
 * @Author Julie
 * @Date 2021/3/31 8:07
 * @Version 1.0
 */
public class RpcPostProcessor implements InstantiationAwareBeanPostProcessor, ApplicationContextAware {

    ApplicationContext context = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        if (bean.getClass().isAnnotationPresent(TRpcService.class)) {
            ServiceConfig serviceConfig = new ServiceConfig();
            serviceConfig.addProtocolConfig(context.getBean(ProtocolConfig.class));
            serviceConfig.addRegistryConfig(context.getBean(RegistryConfig.class));
            serviceConfig.setReference(bean);//具体的实现类

            TRpcService annotation = bean.getClass().getAnnotation(TRpcService.class);
            if (void.class == annotation.interfaceClass()) {
                serviceConfig.setService(bean.getClass().getInterfaces()[0]);
            } else {
                serviceConfig.setService(annotation.interfaceClass());
            }
            try {
                //暴露服务
                RpcBootStrap.export(serviceConfig);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        // 设置引用
        for (Field field : bean.getClass().getDeclaredFields()) {
            try {
                if (!field.isAnnotationPresent(TRpcReferencre.class)) {
                    continue;
                }

                ReferenceConfig referenceConfig = new ReferenceConfig();
                referenceConfig.addProtocolConfig(context.getBean(ProtocolConfig.class));
                referenceConfig.addRegistryConfig(context.getBean(RegistryConfig.class));
                referenceConfig.setService(field.getType());

                //设置负载均衡模式
                TRpcReferencre annotation = field.getAnnotation(TRpcReferencre.class);
                String loadBalance = annotation.loadBalance();
                referenceConfig.setLoadBalance(loadBalance);

                Object referenceBean = RpcBootStrap.getReferenceBean(referenceConfig);
                if (referenceBean == null) {
                    throw new RuntimeException("获取代理对象服务失败！");
                }
                field.setAccessible(true);
                field.set(bean, referenceBean);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return bean;
    }
}
