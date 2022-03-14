package com.zetyun.disruptor;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import com.zetyun.disruptor.common.LongEvent;

import java.nio.ByteBuffer;

public class DisruptorVersion6 {

    public static void main(String[] args) {
        int bufferSize = 8;
        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(
                LongEvent::new,
                bufferSize,
                DaemonThreadFactory.INSTANCE,
                ProducerType.SINGLE,
                new BlockingWaitStrategy());
        disruptor.handleEventsWith(new EventHandler<LongEvent>() {
            @Override
            public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
            }
        });

        RingBuffer ringBuffer = disruptor.getRingBuffer();

    }
}
