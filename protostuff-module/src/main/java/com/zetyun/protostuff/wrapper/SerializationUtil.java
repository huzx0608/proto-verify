package com.zetyun.protostuff.wrapper;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.DefaultIdStrategy;
import io.protostuff.runtime.IdStrategy;
import io.protostuff.runtime.RuntimeSchema;

import java.util.concurrent.ConcurrentHashMap;

public class SerializationUtil {
    private static ConcurrentHashMap<Class<?>, Schema<?>> cachedSchema = new ConcurrentHashMap(256);
    // private static DefaultIdStrategy strategy = new DefaultIdStrategy();
    private static final DefaultIdStrategy strategy = new DefaultIdStrategy(IdStrategy.DEFAULT_FLAGS
            | IdStrategy.PRESERVE_NULL_ELEMENTS
            | IdStrategy.MORPH_COLLECTION_INTERFACES
            | IdStrategy.MORPH_MAP_INTERFACES
            | IdStrategy.MORPH_NON_FINAL_POJOS);

//    static {
//        strategy.registerDelegate(Delegates.DATE_DELEGATE);
//    }

    private static ThreadLocal<ClassWrapper> clazzWrapper = new ThreadLocal<ClassWrapper>(){
        @Override
        protected ClassWrapper initialValue() {
            return new ClassWrapper();
        }
    };

    private SerializationUtil(){}

    private static  Schema  getSchema(Class clz){
        Schema schema = cachedSchema.get(clz);
        if (null == schema){
            schema = RuntimeSchema.getSchema(clz, strategy);
            if (null != schema){
                cachedSchema.put(clz, schema);
            } else {
                throw new IllegalStateException("Failed to create schema for class " + clz);
            }
        }
        return schema;
    }

    /**
     * @param obj
     * @return
     */
    public static byte[] marshall(Object obj){
        clazzWrapper.get().setValue(obj);
        LinkedBuffer buffer = LinkedBuffer.allocate();

        try {
            Schema schema = getSchema(ClassWrapper.class);
            return ProtostuffIOUtil.toByteArray(clazzWrapper.get(), schema, buffer);
        }finally {
            buffer.clear();
        }
    }

    public static Object unmarshall(byte[] bytes){
        if (0 == bytes.length)
            return null;
        Schema schema = getSchema(ClassWrapper.class);
        assert null != schema : "schema is null";
        ClassWrapper message = (ClassWrapper) schema.newMessage();
        ProtostuffIOUtil.mergeFrom(bytes, message, schema);
        return message.getValue();
    }
}
