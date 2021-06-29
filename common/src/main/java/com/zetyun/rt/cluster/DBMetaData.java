package com.zetyun.rt.cluster;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DBMetaData {
    private String dbName;
    private String s3BucketName;
    private String s3BucketPath;
    private Long   lastKafkaMsgTimeStampMs;
}
