package azen.callcenter;

import azen.callcenter.bus.EventBus;
import azen.callcenter.bus.EventType;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        EventBus bus = new EventBus();

        new CallAgency(bus);
        new CallCenter("Muenchen", "089/", bus);
        new CallCenter("Hamburg", "040/", bus);

        new Statistics(bus, EventType.CALL_RECEIVED, EventType.CALL_RECEIVED_COUNT);
        new Statistics(bus, EventType.CALL_ROUTING, EventType.CALL_ROUTING_COUNT);
        new Statistics(bus, EventType.CALL_ANSWERED, EventType.CALL_ANSWERED_COUNT);

        Sourcing sourcing = new Sourcing(bus, EventType.CALL_RECEIVED);

        bus.emit(EventType.CALL_RECEIVED, "089/12345");
        bus.emit(EventType.CALL_RECEIVED, "089/12345");
        bus.emit(EventType.CALL_RECEIVED, "040/12345");
        bus.emit(EventType.CALL_RECEIVED, "0841/12345");
        bus.emit(EventType.CALL_RECEIVED, "0841/12345");
        bus.emit(EventType.CALL_RECEIVED, "0841/12345");

        bus.emit(EventType.SOURCING_PRINT_RECORD, null);

        System.out.println("do it again");
        List<Object> events = sourcing.events();
        events.forEach(payload -> bus.emit(sourcing.sourcingEvent(), payload));
    }
}
