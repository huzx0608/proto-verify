package com.zetyun.dingo.avo;

import org.apache.avro.reflect.AvroEncode;
import org.apache.avro.reflect.DateAsLongEncoding;

public class AvroEncRecord {
    @AvroEncode(using = DateAsLongEncoding.class)
    java.util.Date date;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AvroEncRecord))
            return false;
        return date.equals(((AvroEncRecord) o).date);
    }

    @Override
    public String toString() {
        return "AvroEncRecord{" +
                "date=" + date +
                '}';
    }
}
