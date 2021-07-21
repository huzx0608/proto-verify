package com.zetyun.example.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.rocksdb.CloudWalMeta;
import org.rocksdb.CloudWalUtils;
import org.rocksdb.RocksDB;

import java.nio.ByteBuffer;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class KafkaConsumerExample {

    static {
        RocksDB.loadLibrary();
    }

    public static void main(String[] args) {
        String bootstrapServers = "172.20.3.83:9092";
        String grp_id = "huzx_consumer_01";
        String topic = "huzx10.rocksdb.cloud.durable.example";
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
         properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class.getName());
         properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, grp_id);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        KafkaConsumer<byte[], byte[]> consumer = new KafkaConsumer<byte[], byte[]>(properties);
        consumer.subscribe(Arrays.asList(topic));
        while (true) {
            ConsumerRecords<byte[], byte[]> records = consumer.poll(Duration.ofMillis(10000));
            for (ConsumerRecord<byte[], byte[]> record : records) {
                CloudWalMeta meta = new CloudWalUtils().extractLogRecord(record.value());
                System.out.println("xxxx=> Fetch meta is:" +  meta.toString());
            }
        }
    }

    public static int fromByteArray01(byte[] bytes) {
        return bytes[0] << 24 | (bytes[1] & 0xFF) << 16 | (bytes[2] & 0xFF) << 8 | (bytes[3] & 0xFF);
    }

    public static int fromByteArray02(byte[] bytes) {
        return ((bytes[3] & 0xFF) << 24) |
                ((bytes[2] & 0xFF) << 16) |
                ((bytes[1] & 0xFF) << 8 ) |
                ((bytes[0] & 0xFF) << 0 );
    }

    public static int extractFix32Int(byte[] input) {
        byte[] fix32Int00 = Arrays.copyOfRange(input, 0, 4);
        byte[] fix32Int01 =  {input[3], input[2], input[1], input[0]};

        int value00 = KafkaConsumerExample.fromByteArray01(fix32Int00);
        int value01 = KafkaConsumerExample.fromByteArray02(fix32Int00);
        System.out.println("1==>value00 =>" + value00 + ", value01:" + value01);

        value00 = KafkaConsumerExample.fromByteArray01(fix32Int01);
        value01 = KafkaConsumerExample.fromByteArray02(fix32Int01);
        System.out.println("2==>value00 =>" + value00 + ", value01:" + value01);
        return value01;
    }

    public static long extractFix64Long(byte[] input) {
        byte[] fix64Long = Arrays.copyOfRange(input, 4, 4 + 8);
        ByteBuffer wrapped = ByteBuffer.wrap(fix64Long);
        Long value = wrapped.getLong();
        return value;
    }

    public static void extractWalLog(byte[] inputBytes) {
        int opType = extractFix32Int(inputBytes);
        System.out.println("Current OpType is:" + opType);

        switch (opType) {
            case 1: {
                Long position = extractFix64Long(inputBytes);
                System.out.println("Current OpType is:" + opType + ", Offset of File is:" + position);
                break;
            }
            case 2: {
                break;
            }
            case 4: {
                break;
            }
        }
    }
}
