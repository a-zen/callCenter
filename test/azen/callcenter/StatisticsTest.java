package azen.callcenter;

import azen.callcenter.bus.EventBus;
import azen.callcenter.bus.EventType;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;

public class StatisticsTest {

    private EventBus bus = new EventBus();

    @Test
    public void shouldEmitCount() {
        AtomicBoolean called = new AtomicBoolean();
        EventType subscribeType = EventType.CALL_RECEIVED;
        EventType emitType = EventType.CALL_RECEIVED_COUNT;
        new Statistics(bus, subscribeType, emitType);
        bus.subscribe(emitType, __ -> {
            called.set(true);
        });

        bus.emit(subscribeType, null);

        assert called.get();
    }

    @Test
    public void shouldCount() {
        EventType subscribeType = EventType.CALL_RECEIVED;
        EventType emitType = EventType.CALL_RECEIVED_COUNT;
        new Statistics(bus, subscribeType, emitType);
        AtomicInteger counter = new AtomicInteger();
        bus.subscribe(emitType, __ -> {
            counter.set((Integer) __);
        });

        bus.emit(subscribeType, null);

        assertEquals(1, counter.get());
    }

    @Test
    public void shouldCountTwice() {
        EventType subscribeType = EventType.CALL_RECEIVED;
        EventType emitType = EventType.CALL_RECEIVED_COUNT;
        new Statistics(bus, subscribeType, emitType);
        AtomicInteger counter = new AtomicInteger();
        bus.subscribe(emitType, __ -> {
            counter.set((Integer) __);
        });

        bus.emit(subscribeType, null);
        bus.emit(subscribeType, null);

        assertEquals(2, counter.get());
    }

}