package com.ibicd.rpc.test.serialize;

import com.ibicd.rpc.common.serialize.Serialization;
import com.ibicd.rpc.common.tools.SpiUtils;
import com.ibicd.rpc.test.TestClass;

import java.util.Arrays;

/**
 * @ClassName JsonSerializationTest
 * @Description TODO
 * @Author Julie
 * @Date 2021/3/31 7:52
 * @Version 1.0
 */
public class JsonSerializationTest {

    public static void main(String[] args) throws Exception {
        TestClass testClass = new TestClass();
        testClass.setName("Tony");
        testClass.setAge(18);

        Serialization jsonSerialization =
                (Serialization) SpiUtils.getServiceImpl("JsonSerialization", Serialization.class);
        byte[] serialize = jsonSerialization.serialize(testClass);
        System.out.println(Arrays.toString(serialize));

        TestClass deserialize = (TestClass) jsonSerialization.deserialize(serialize, TestClass.class);
        System.out.println(deserialize);

    }
}
