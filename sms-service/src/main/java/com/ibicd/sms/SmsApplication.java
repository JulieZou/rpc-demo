package com.ibicd.sms;

import com.ibicd.api.SmsService;
import com.ibicd.rpc.config.spring.annotation.EnableRPC;
import com.ibicd.sms.service.SmsServiceImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;

@Configuration
@ComponentScan("com.ibicd.sms")
@PropertySource("classpath:/rpc.properties")
//@SpringBootApplication
@EnableRPC
public class SmsApplication {

    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SmsApplication.class);
        context.start();

        SmsService smsService = context.getBean(SmsServiceImpl.class);
        System.out.println(smsService.send("10086", "启动时发送一条短信"));


        System.in.read();
        context.close();

    }

}
