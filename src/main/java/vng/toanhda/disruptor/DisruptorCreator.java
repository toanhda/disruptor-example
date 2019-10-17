package vng.toanhda.disruptor;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;

public class DisruptorCreator {
    public static final String DISRUPTOR_NAME_GET = "Get";
    private static Disruptor disruptor;
    private static RingBuffer ringBuffer;

    public static <T> Disruptor getInstance(String disruptorName, EventFactory<T> factory, int bufferSize, EventHandler<T>... handlers) {
        if (disruptor == null) {
            disruptor = newInstance(disruptorName, factory, bufferSize, handlers);
        }
        return disruptor;
    }

    public static RingBuffer getRingBuffer() {
        return ringBuffer;
    }

    public static <T> Disruptor newInstance(String disruptorName, EventFactory<T> factory, int bufferSize, EventHandler<T>... handlers) {
        Disruptor disruptor = new Disruptor(factory,
                bufferSize,
                DaemonThreadFactory.INSTANCE,
                ProducerType.SINGLE,
                new YieldingWaitStrategy());

        disruptor.handleEventsWith(handlers);
        ringBuffer = disruptor.start();

        return disruptor;
    }
}

