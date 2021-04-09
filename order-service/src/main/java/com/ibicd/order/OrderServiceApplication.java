package com.ibicd.order;

import com.ibicd.api.OrderService;
import com.ibicd.rpc.config.spring.annotation.EnableRPC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;
import java.util.concurrent.CyclicBarrier;

@Configuration
@ComponentScan("com.ibicd.order")
@PropertySource("classpath:/rpc.properties")
@EnableRPC
//@SpringBootApplication
public class OrderServiceApplication {

    public static void main(String[] args) throws IOException {
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(OrderServiceApplication.class);
        context.start();

        final CyclicBarrier cyclicBarrier = new CyclicBarrier(1);
        for (int i = 0; i < 1; i++) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        cyclicBarrier.await();
                        // 测试..模拟调用接口 -- 一定是远程，因为当前的系统没有具体实现类
                        OrderService orderService = context.getBean(OrderService.class);
                        orderService.create("买一瓶水");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }


        // 阻塞不退出
        System.in.read();
        context.close();

    }

}
