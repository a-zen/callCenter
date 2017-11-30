package azen.callcenter;

import azen.callcenter.bus.EventBus;
import azen.callcenter.bus.EventType;

public final class CallAgency {
    private final EventBus bus;

    public CallAgency(EventBus bus) {
        this.bus = bus;
        this.subscribe();
    }

    private void subscribe() {
        bus.subscribe(EventType.CALL_RECEIVED, this::routeCall);
    }

    private <T> void routeCall(T number) {
        bus.emit(EventType.CALL_ROUTING, number);
    }
}
