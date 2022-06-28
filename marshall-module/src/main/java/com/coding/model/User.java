package com.coding.model;


import com.coding.codec.InstantAvroEncoding;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.avro.reflect.AvroEncode;
import org.apache.avro.reflect.Nullable;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private String name;
    private int age;
    private List<String> phoneNumbers;

    @Nullable
    private String company;

    @AvroEncode(using = InstantAvroEncoding.class)
    private Instant lastUpdateTime;
}
