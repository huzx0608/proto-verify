package com.zetyun.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;
import com.zetyun.disruptor.common.LongEvent;

import java.nio.ByteBuffer;

public class DisruptorVersion2 {

    public static void handleEvent(LongEvent event, long sequence, boolean endOfBatch) {
        System.out.println("==> Event:" + event.getValue());
    }

    public static void translateTo(LongEvent event, long sequence, ByteBuffer bb) {
        event.set(bb.getLong(0));
    }

    public static void main(String[] args) {
        int bufferSize = 1024;
        Disruptor<LongEvent> disruptor = new Disruptor<>(LongEvent::new, bufferSize, DaemonThreadFactory.INSTANCE);
        disruptor.handleEventsWith(DisruptorVersion2::handleEvent);
        disruptor.start();

        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        ByteBuffer bb = ByteBuffer.allocate(8);

        for (long l = 0; true; l++) {
            bb.putLong(0, l);
            ringBuffer.publishEvent(DisruptorVersion2::translateTo, bb);
        }
    }
}
