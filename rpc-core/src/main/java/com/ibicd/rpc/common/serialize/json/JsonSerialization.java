package com.ibicd.rpc.common.serialize.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ibicd.rpc.common.serialize.Serialization;

import java.text.SimpleDateFormat;

/**
 * @ClassName JsonSerialization
 * @Description TODO
 * @Author Julie
 * @Date 2021/3/30 23:57
 * @Version 1.0
 */
public class JsonSerialization implements Serialization {

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        // 将对象所有的属性列入
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        // 取消间日期转换为timestamp
        objectMapper.disable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);
        //忽略空bean 转json 的错误
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        //统一日期格式
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        // 忽略 在json字符串中存在, 但是在java对象中不存在对应属性的情况, 防止出错
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public byte[] serialize(Object output) throws Exception {
        return objectMapper.writeValueAsBytes(output);
    }

    @Override
    public Object deserialize(byte[] input, Class clazz) throws Exception {
        Object o = objectMapper.readValue(input, clazz);
        return o;
    }
}
