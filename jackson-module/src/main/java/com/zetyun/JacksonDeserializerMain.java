package com.zetyun;

import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zetyun.modle.deser.AliasBean;
import com.zetyun.modle.deser.BeanWithCreator;
import com.zetyun.modle.deser.BeanWithInject;
import com.zetyun.modle.deser.BeanWithSetter;
import com.zetyun.modle.deser.EventWithDeSerializer;
import com.zetyun.modle.deser.ExtendableBean;

import java.text.SimpleDateFormat;

public class JacksonDeserializerMain {

    public static void main(String[] args) throws Exception{
        // case 1:
        String json = "{\"TheName\":\"jackson\",\"id\":20}";
        BeanWithCreator beanWithCreator = new ObjectMapper().readValue(json, BeanWithCreator.class);
        System.out.println(beanWithCreator);
        System.out.println("==============================");

       // case 2:
        String json2 = "{\"name\":\"jackson\"}";
        InjectableValues injectableValues = new InjectableValues.Std().addValue(int.class, 20);
        BeanWithInject beanWithInject = new ObjectMapper()
                .reader(injectableValues)
                .forType(BeanWithInject.class)
                .readValue(json2);
        System.out.println(beanWithInject);
        System.out.println("==============================");

        // case 3:
        String json3 = "{\"name\":\"My bean\",\"attr2\":\"val2\",\"attr1\":\"val1\"}";
        ExtendableBean bean = new ObjectMapper().readValue(json3, ExtendableBean.class);
        System.out.println(bean);
        System.out.println("==============================");

        // case 4:
        String json4 = "{\"id\":1,\"name\":\"My bean\"}";
        BeanWithSetter beanWithSetter = new ObjectMapper().readValue(json4, BeanWithSetter.class);
        System.out.println(beanWithSetter);
        System.out.println("==============================");

        // case 5:
        String json5 = "{\"name\":\"party\", \"eventDate\":\"20-12-2014 02:30:00\"}";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        EventWithDeSerializer eventWithDeSerializer = new ObjectMapper()
                .readerFor(EventWithDeSerializer.class)
                .readValue(json5);
        System.out.println(eventWithDeSerializer);

        // case 6:
        String json6 = "{\"fName\": \"John\", \"lastName\": \"Green\"}";
        AliasBean aliasBean = new ObjectMapper().readValue(json6, AliasBean.class);
        System.out.println(aliasBean);
    }
}
