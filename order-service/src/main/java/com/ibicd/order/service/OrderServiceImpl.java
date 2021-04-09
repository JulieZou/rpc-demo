package com.ibicd.order.service;

import com.ibicd.api.OrderService;
import com.ibicd.api.SmsService;
import com.ibicd.rpc.config.annotation.TRpcReferencre;
import org.springframework.stereotype.Service;

/**
 * @ClassName OrderServiceImpl
 * @Description TODO
 * @Author Julie
 * @Date 2021/3/30 23:41
 * @Version 1.0
 */
@Service
public class OrderServiceImpl implements OrderService {

    //引用一个远程的服务，
    @TRpcReferencre
    SmsService smsService;


    @Override
    public void create(String content) {

        System.out.println("订单创建成功");
        Object sendResult = smsService.send("10086", "今天去哪吃饭？");
        System.out.println("订单调用结果：" + sendResult);
    }
}
