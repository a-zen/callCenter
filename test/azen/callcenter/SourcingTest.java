package azen.callcenter;

import azen.callcenter.bus.EventBus;
import azen.callcenter.bus.EventType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SourcingTest {

    private EventBus bus = new EventBus();
    private EventType sourcingEvent = EventType.CALL_RECEIVED;

    @Test
    public void shouldRecordEvent() {
        String number = "089/12345";
        Sourcing sourcing = new Sourcing(bus, sourcingEvent);

        bus.emit(sourcingEvent, number);

        assertEquals(1, sourcing.events().size());
        assertEquals(number, sourcing.events().get(0));
    }

}