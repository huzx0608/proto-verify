package com.zetyun.disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.SingleProducerSequencer;
import com.lmax.disruptor.WaitStrategy;

public class DisruptorVersion7 {
    public static void main(String[] args) {
        WaitStrategy waitStrategy = new BlockingWaitStrategy();
        SingleProducerSequencer sequencer = new SingleProducerSequencer(8, waitStrategy);
        for (int i = 0; i < 32; i++) {
            long sequence = sequencer.next();
            System.out.println("===> P => sequence:" + sequence + ", >>> " + System.currentTimeMillis());
            sequencer.publish(sequence);
        }
    }
}
