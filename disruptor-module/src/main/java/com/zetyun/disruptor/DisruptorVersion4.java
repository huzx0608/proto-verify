package com.zetyun.disruptor;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;
import com.zetyun.disruptor.common.LongEvent;

import java.nio.ByteBuffer;


public class DisruptorVersion4 {

    private static class Producer {
        private final RingBuffer<LongEvent> ringBuffer;

        public Producer(final RingBuffer ringBuffer) {
            this.ringBuffer = ringBuffer;
        }

        public static final EventTranslatorOneArg<LongEvent, ByteBuffer> TRANSLATOR = new EventTranslatorOneArg<LongEvent, ByteBuffer>() {
            @Override
            public void translateTo(LongEvent event, long sequence, ByteBuffer bb) {
                event.set(bb.getLong(0));
            }
        };

        public void onPublish(ByteBuffer bb) {
            // 1. implement by one
            // ringBuffer.publishEvent(TRANSLATOR, bb);
            long sequence = ringBuffer.next();
            try {
                LongEvent event = ringBuffer.get(sequence);
                event.set(bb.getLong(0));
            } finally {
                ringBuffer.publish(sequence);
            }
        }
    }

    public static void main(String[] args) {
        int bufferSize = 8;
        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(LongEvent::new, bufferSize, DaemonThreadFactory.INSTANCE);
        disruptor.handleEventsWith(new EventHandler<LongEvent>() {
            @Override
            public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
                System.out.println("C=====>Sequence:" + sequence + ", Event:" + event.getValue());
                Thread.sleep(1000);
            }
        });
        disruptor.start();

        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        Producer producer = new Producer(ringBuffer);
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);

        for (long l = 0; true; l++) {
            byteBuffer.putLong(0, l);
            producer.onPublish(byteBuffer);
            System.out.println("P===>Producer:" + l);
        }
    }
}
