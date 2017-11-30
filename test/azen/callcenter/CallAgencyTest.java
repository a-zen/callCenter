package azen.callcenter;

import azen.callcenter.bus.EventBus;
import azen.callcenter.bus.EventType;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

@SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
public class CallAgencyTest {

    @Test
    public void callTheAgencyShouldEmitRoutingEvent() {
        EventBus bus = new EventBus();
        new CallAgency(bus);
        AtomicBoolean called = new AtomicBoolean();
        bus.subscribe(EventType.CALL_ROUTING, __ -> called.set(true));

        bus.emit(EventType.CALL_RECEIVED, "040/234");

        assert called.get();
    }

}