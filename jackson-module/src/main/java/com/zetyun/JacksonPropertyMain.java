package com.zetyun;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.zetyun.modle.*;

import java.io.IOException;

public class JacksonPropertyMain {

    public static void main(String[] args) throws IOException {
        // case 1:
        ExtendableBean bean = new ExtendableBean("myBean");
        bean.add("key1", "value1");
        bean.add("key2", "value2");
        String value = new ObjectMapper().writeValueAsString(bean);
        System.out.println(value);
        System.out.println("==============================");

        // case 2:
        MyBean myBean = new MyBean(1, "myBean");
        String result = new ObjectMapper().writeValueAsString(myBean);
        System.out.println(result);
        System.out.println("==============================");

        // case 3:
        RawBean rawBean = new RawBean("myBean", "{\"attr\":false}");
        String result2 = new ObjectMapper().writeValueAsString(rawBean);
        System.out.println(result2);
        System.out.println("==============================");

        // case 4:
        String result4 = new ObjectMapper().writeValueAsString(TypeEnumWithValue.TYPE1);
        System.out.println(result4);
        System.out.println("==============================");

        // case 5:
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        String result5 = mapper.writeValueAsString(new UserWithRoot(1, "123456"));
        System.out.println(result5);
        System.out.println("==============================");
    }
}
