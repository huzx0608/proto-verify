package com.zetyun;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.zetyun.modle.ser.EventWithSerializer;
import com.zetyun.modle.ser.ExtendableBean;
import com.zetyun.modle.ser.MyBean;
import com.zetyun.modle.ser.RawBean;
import com.zetyun.modle.ser.TypeEnumWithValue;
import com.zetyun.modle.ser.UserWithRoot;

import java.text.SimpleDateFormat;

public class JacksonSerializerMain {

    public static void main(String[] args) throws Exception{
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

        // case 6:
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String toParse = "2020-10-10 10:10:10";
        java.util.Date date = format.parse(toParse);
        EventWithSerializer event = new EventWithSerializer("name", date);
        String result6 = new ObjectMapper().writeValueAsString(event);
        System.out.println(result6);
        System.out.println("==============================");
    }
}
