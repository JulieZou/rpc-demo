package com.ibicd.sms.service;

import com.ibicd.api.SmsService;
import com.ibicd.rpc.config.annotation.TRpcService;

import java.util.UUID;

/**
 * @ClassName SmsServiceImpl
 * @Description TODO
 * @Author Julie
 * @Date 2021/3/30 23:44
 * @Version 1.0
 */
@TRpcService
public class SmsServiceImpl implements SmsService {
    @Override
    public Object send(String phone, String content) {
        System.out.println("发送短信成功：");
        return "短信发送成功：" + UUID.randomUUID();
    }
}
