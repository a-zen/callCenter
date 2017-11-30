package azen.callcenter;

import azen.callcenter.bus.EventBus;
import azen.callcenter.bus.EventType;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

public class CallCenterTest {

    private EventBus bus = new EventBus();
    private String prefix = "040/";
    private CallCenter test = new CallCenter("test", prefix, bus);

    @Test
    public void callCenterShouldAnswerCall() {
        AtomicBoolean called = new AtomicBoolean();
        bus.subscribe(EventType.CALL_ANSWERED, __ -> called.set(true));

        bus.emit(EventType.CALL_ROUTING, String.format("%s12345", prefix));

        assert called.get();
    }

    @Test
    public void callCenterShouldNotAnswerCallForOtherCenter() {
        AtomicBoolean called = new AtomicBoolean();
        bus.subscribe(EventType.CALL_ANSWERED, __ -> called.set(true));

        bus.emit(EventType.CALL_ROUTING, "089/12345");

        assert !called.get();
    }
}