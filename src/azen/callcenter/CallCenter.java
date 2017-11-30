package azen.callcenter;

import azen.callcenter.bus.EventBus;
import azen.callcenter.bus.EventType;

public final class CallCenter {
    private final String prefix;
    private final String name;
    private final EventBus bus;

    public CallCenter(String name, String prefix, EventBus bus) {
        this.name = name;
        this.prefix = prefix;
        this.bus = bus;
        this.subscribe();
    }

    private void subscribe() {
        bus.subscribe(EventType.CALL_ROUTING, this::handleEvent);
    }

    private void handleEvent(String payload) {
        // check if call is actual for us
        if (payload.startsWith(prefix)) {
            // handle event
            sendNotification();
            System.out.println(String.format("%s received call from %s", name, payload));
        }
    }

    private void sendNotification() {
        bus.emit(EventType.CALL_ANSWERED, null);
    }
}
