package com.ibicd.rpc.config.spring;

import com.ibicd.rpc.config.ProtocolConfig;
import com.ibicd.rpc.config.RegistryConfig;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.reflect.Field;

/**
 * @ClassName RpcConfiguration
 * @Description TODO
 * @Author Julie
 * @Date 2021/3/31 8:11
 * @Version 1.0
 */
public class RpcConfiguration implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private StandardEnvironment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = (StandardEnvironment) environment;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(ProtocolConfig.class);
        for (Field declaredField : ProtocolConfig.class.getDeclaredFields()) {
            String value = environment.getProperty("rpc.protocol." + declaredField.getName());
            builder.addPropertyValue(declaredField.getName(), value);
        }
        registry.registerBeanDefinition("protocolConfig", builder.getBeanDefinition());

        builder = BeanDefinitionBuilder.genericBeanDefinition(RegistryConfig.class);
        for (Field declaredField : RegistryConfig.class.getDeclaredFields()) {
            String value = environment.getProperty("rpc.registry." + declaredField.getName());
            builder.addPropertyValue(declaredField.getName(), value);
        }
        registry.registerBeanDefinition("registryConfig", builder.getBeanDefinition());

    }


}
