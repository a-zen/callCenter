package azen.callcenter;

import azen.callcenter.bus.EventBus;
import azen.callcenter.bus.EventType;

public class Statistics {

    private final EventBus bus;
    private final EventType subscribeType;
    private final EventType emitType;
    private int counter;

    public Statistics(EventBus bus, EventType subscribeType, EventType emitType) {
        this.bus = bus;
        this.subscribeType = subscribeType;
        this.emitType = emitType;
        counter = 0;
        subscribe();
    }

    private void subscribe() {
        bus.subscribe(subscribeType, this::counter);
    }

    private <T> void counter(T payload) {
        counter++;
        System.out.println(String.format("statistics update for %s: %d", subscribeType, counter));
        sendNotification();

    }

    private void sendNotification() {
        bus.emit(emitType, counter);
    }
}
