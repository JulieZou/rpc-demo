package com.ibicd.rpc.config.spring.annotation;

import com.ibicd.rpc.config.spring.RpcConfiguration;
import com.ibicd.rpc.config.spring.RpcPostProcessor;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用在启动类上，表示启用RPC
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({RpcPostProcessor.class, RpcConfiguration.class})
public @interface EnableRPC {
}
