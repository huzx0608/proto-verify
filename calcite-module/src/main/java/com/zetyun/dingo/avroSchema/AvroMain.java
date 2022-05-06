package com.zetyun.dingo.avroSchema;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;

public class AvroMain {

    private final Schema schema;
    private final DatumReader<GenericRecord> reader;
    private final DatumWriter<GenericRecord> writer;

    public AvroMain(Schema schema) {
        this.schema = schema;
        this.reader = new GenericDatumReader<>(schema);
        this.writer = new GenericDatumWriter<>(schema);
    }
}
