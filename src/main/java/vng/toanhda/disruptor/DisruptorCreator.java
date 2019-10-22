package vng.toanhda.disruptor;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;

import java.util.ArrayList;

public class DisruptorCreator {
    public static final String DISRUPTOR_NAME_GET = "Get";
    private static Disruptor disruptor;
    private static RingBuffer ringBuffer;

    public static <T> Disruptor getInstance(String disruptorName, EventFactory<StorageEvent> factory, int bufferSize, StorageConsumer handlers) {
        if (disruptor == null) {
            disruptor = newInstance(disruptorName, factory, bufferSize, handlers);
        }
        return disruptor;
    }

    public static RingBuffer getRingBuffer() {
        return ringBuffer;
    }

    public static <T> Disruptor newInstance(String disruptorName, EventFactory<StorageEvent> factory, int bufferSize, StorageConsumer handlers) {
        Disruptor disruptor = new Disruptor(factory,
                bufferSize,
                DaemonThreadFactory.INSTANCE,
                ProducerType.SINGLE,
                new BusySpinWaitStrategy());

//        disruptor.handleEventsWith(handlers);

        disruptor.handleEventsWithWorkerPool(new StorageConsumer(),
                new StorageConsumer(),
                new StorageConsumer(),
                new StorageConsumer(),
                new StorageConsumer(),
                new StorageConsumer(),
                new StorageConsumer(),
                new StorageConsumer(),
                new StorageConsumer(),
                new StorageConsumer(),
                new StorageConsumer(),
                new StorageConsumer(),
                new StorageConsumer(),
                new StorageConsumer(),
                new StorageConsumer(),
                new StorageConsumer());
        ringBuffer = disruptor.start();

        return disruptor;
    }
}

