package com.ibicd.api;

/**
 * @ClassName SmsService
 * @Description 短信发送API
 * @Author Julie
 * @Date 2021/3/30 22:51
 * @Version 1.0
 */
public interface SmsService {

    /**
     * 发送短信
     *
     * @param phone
     * @param content
     * @return
     */
    Object send(String phone, String content);
}
