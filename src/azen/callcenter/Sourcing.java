package azen.callcenter;

import azen.callcenter.bus.EventBus;
import azen.callcenter.bus.EventType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Sourcing {

    private final EventBus bus;
    private final EventType sourcingEvent;
    private List<Object> events;

    public Sourcing(EventBus bus, EventType sourcingEvent) {
        this.bus = bus;
        this.sourcingEvent = sourcingEvent;
        this.events = new CopyOnWriteArrayList<>();
        subscribe();
    }

    public List<Object> events() {
        return events;
    }

    public EventType sourcingEvent() {
        return sourcingEvent;
    }

    private void subscribe() {
        bus.subscribe(sourcingEvent, this::record);
        bus.subscribe(EventType.SOURCING_PRINT_RECORD, this::print);
    }

    private <T> void record(T payload) {
        events.add(payload);
    }

    private <T> void print(T payload) {
        System.out.println(String.format("Recorded events with type: %s", sourcingEvent));
        System.out.println(events);
    }
}
