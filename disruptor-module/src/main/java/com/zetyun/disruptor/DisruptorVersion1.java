package com.zetyun.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;
import com.zetyun.disruptor.common.LongEvent;

import java.nio.ByteBuffer;

public class DisruptorVersion1 {

    public static void main(String[] args) throws InterruptedException {
        int bufferSize = 1024;

        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(LongEvent::new,
                bufferSize, DaemonThreadFactory.INSTANCE);

        disruptor.handleEventsWith((event,sequence,endOfBatch) -> {
            try {
                System.out.println("===> Event:" + event.getValue());
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });
        disruptor.start();

        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        ByteBuffer bb = ByteBuffer.allocate(8);
        for (long l = 0; true; l++) {
            bb.putLong(0, l);
            System.out.println("producer=>" + l);

            /*
            // 1. publishEvent with parameters
            ringBuffer.publishEvent((event, sequence, buffer) -> event.set(buffer.getLong(0)), bb);
             */
            // 2. publishEvent without parameter
            ringBuffer.publishEvent(((event, sequence) -> event.set(bb.getLong(0))));
        }
    }
}
