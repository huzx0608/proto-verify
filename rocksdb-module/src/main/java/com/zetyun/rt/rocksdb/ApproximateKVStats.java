package com.zetyun.rt.rocksdb;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApproximateKVStats {
    Long keysCnt;
    Long sizeInBytes;

    @Override
    public String toString() {
        return "ApproximateKVStats{" +
                "keysCnt=" + keysCnt +
                ", sizeInBytes=" + sizeInBytes +
                '}';
    }
}
