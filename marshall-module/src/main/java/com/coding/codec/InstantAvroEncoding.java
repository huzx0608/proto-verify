package com.coding.codec;

import org.apache.avro.SchemaBuilder;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.Encoder;
import org.apache.avro.reflect.CustomEncoding;

import java.io.IOException;
import java.time.Instant;

public class InstantAvroEncoding extends CustomEncoding<Instant> {

    public InstantAvroEncoding() {
        this.schema = SchemaBuilder.builder()
                .stringBuilder()
                .prop("CustomEncoding", InstantAvroEncoding.class.getName())
                .endString();
    }

    @Override
    protected void write(Object datum, Encoder out) throws IOException {
        out.writeString(datum.toString());
    }

    @Override
    protected Instant read(Object reuse, Decoder in) throws IOException {
        try {
            return Instant.parse(in.readString());
        } catch (Exception ex) {
            throw new IllegalStateException("Could not decode String to Instance", ex);
        }
    }
}
