package cn.corechan.travel.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class Jackson {
    private static ObjectMapper objectMapper;

    /**
     * 解析json
     *
     * @param content 前端传入的文本
     * @param valueType 值的类型
     * @return 传入类型的对象
     */
    public static <T> T fromJson(String content, Class<T> valueType) {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }

        try {
            return objectMapper.readValue(content, valueType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 转化成json
     *
     * @param object 被转化的对象
     * @return 转化的文本
     */
    public static String toJson(Object object) {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }

        try {
            return objectMapper.writeValueAsString(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
