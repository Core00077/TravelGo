package cn.corechan.travel.util.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JacksonDemo {
    //私有静态实例，赋值为空，延迟加载
    private static ObjectMapper objectMapper=null;
    //私有构造，防止实例化
    private JacksonDemo(){}
    //静态方法创建单个实例
    public static ObjectMapper getObjectMapper(){
        if(objectMapper==null){
            objectMapper=new ObjectMapper();
        }
        return objectMapper;
    }

    private static String toJson(Object object) throws JsonProcessingException {
        //java对象序列化为Json字符串
        return getObjectMapper().writeValueAsString(object);
    }

    private static <T> T  fromJson(String json,Class<T> type) throws IOException {
        //将Json字符串反序列化为java对象
        return getObjectMapper().readValue(json,type);
    }

    public static void main(String[] args) {
        //status是一个有三个私有属性的类，分别是String status,String msg,Object data;
        Status status=new Status();
        status.setData("abc");
        try {
            //将status输出为json字符串
            System.out.println(toJson(status));
            //将json字符串转换为Status对象
            String json="{\"status\":\"success\",\"msg\":\"seller goods found successfully!\",\"data\":[{\"id\":\"014352373888\",\"name\":\"%E4%BC%91%E9%97%B2%E5%A8%B1%E4%B9%90+%E6%91%84%E5%BD%B1%E4%B9%8B%E5%9C%B0\",\"price\":128.0,\"city\":null,\"route\":null,\"pictures\":[\"/img/goods/014352373888/1.png\"],\"description\":null,\"comment\":null,\"seller\":{\"headPicture\":\"/img/headPic.png\",\"phoneNumber\":\"11111111111\",\"sex\":null,\"name\":\"testuser\"},\"pubtime\":\"1514523738880\"}]}";
            Object s=fromJson(json,Object.class);
            System.out.println(s.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
