package azen.callcenter.bus;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

public class EventBusTest {

    private EventType type = EventType.CALL_RECEIVED;

    @Test
    public void busEmitsEventsAndReceiverGetsNotified() {
        EventBus bus = new EventBus();
        AtomicBoolean called = new AtomicBoolean();
        bus.subscribe(type, __ -> called.set(true));
        bus.emit(type, null);
        assert called.get();
    }

    @Test
    public void listenersDoNotGetNotifiedOnUnrelatedEvents() {
        EventBus bus = new EventBus();
        AtomicBoolean called = new AtomicBoolean();
        bus.subscribe(type, __ -> called.set(true));
        bus.emit(EventType.CALL_ROUTING, null);
        assert !called.get();
    }

    @Test
    public void multipleReceiversGetNotified() {
        EventBus bus = new EventBus();
        AtomicBoolean called1 = new AtomicBoolean();
        AtomicBoolean called2 = new AtomicBoolean();
        bus.subscribe(type, __ -> called1.set(true));
        bus.subscribe(type, __ -> called2.set(true));
        bus.emit(type, null);
        assert called1.get();
        assert called2.get();
    }


    @Test(expected = IllegalStateException.class)
    public void whenFirstListenerThrowsSecondIsStillCalled() {
        EventBus bus = new EventBus();
        AtomicBoolean called2 = new AtomicBoolean();
        bus.subscribe(type, __ -> {
            throw new IllegalStateException("don't call me again");
        });
        bus.subscribe(type, __ -> called2.set(true));
        try {
            bus.emit(type, null);
        } finally {
            assert called2.get();
        }
    }

    @Test
    public void eventHasPayload() {
        EventBus bus = new EventBus();
        bus.subscribe(type, payload -> {
            assert payload.equals(42);
        });
        bus.emit(type, 42);
    }
}
