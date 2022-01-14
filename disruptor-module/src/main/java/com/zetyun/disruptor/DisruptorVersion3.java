package com.zetyun.disruptor;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;
import com.zetyun.disruptor.common.LongEvent;

import java.nio.ByteBuffer;
import java.util.Properties;

public class DisruptorVersion3 {
    private static class Producer {
        private final RingBuffer<LongEvent> ringBuffer;

        public Producer(RingBuffer<LongEvent> ringBuffer) {
            this.ringBuffer = ringBuffer;
        }

        public static final EventTranslatorOneArg<LongEvent, ByteBuffer> TRANSLATOR = new EventTranslatorOneArg<LongEvent, ByteBuffer>() {
            @Override
            public void translateTo(LongEvent event, long sequence, ByteBuffer arg0) {
                event.set(arg0.getLong(0));
            }
        };

        public void onPublish(ByteBuffer bb) {
            ringBuffer.publishEvent(TRANSLATOR, bb);
        }
    };

    public static void main(String[] args) {
        int bufferSize = 8;
        Disruptor<LongEvent> disruptor = new Disruptor(LongEvent::new, bufferSize, DaemonThreadFactory.INSTANCE);
        disruptor.handleEventsWith((event, sequence, endOfBatch) -> {
                System.out.println("==>Consumer:" + event.getValue());
                Thread.sleep(1000);
            }
        );
        disruptor.start();
        RingBuffer ringBuffer = disruptor.getRingBuffer();
        Producer producer = new Producer(ringBuffer);

        ByteBuffer bb = ByteBuffer.allocate(8);
        for (long l = 0; true; l++) {
            bb.putLong(0, l);
            System.out.println("++++=>Producer:" + l);
            producer.onPublish(bb);
        }
    }
}
