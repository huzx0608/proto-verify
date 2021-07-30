package com.zetyun.example.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.rocksdb.*;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class KafkaConsumerExample {

    private static String db_path = "./examples/data/rocksdb-data";
    static {
        RocksDB.loadLibrary();
    }

    public static void main(String[] args) throws Exception {
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

        final Options options = new Options().setCreateIfMissing(true);
        final RocksDB rocksDB = RocksDB.open(options, db_path);

        while (true) {
            ConsumerRecords<byte[], byte[]> records = consumer.poll(Duration.ofMillis(10000));
            for (ConsumerRecord<byte[], byte[]> record : records) {
                CloudWalMeta meta = new CloudWalUtils().extractLogRecord(record.value());
                System.out.println("xxxx=> Fetch meta is:" +  meta.toString());
                WriteBatch batch = meta.getBatchRecord();
                if (batch != null && batch.count() > 0) {
                    rocksDB.write(new WriteOptions(), batch);
                    rocksDB.flush(new FlushOptions());
                }
            }
        }
    }
}
