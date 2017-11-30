package azen.callcenter.bus;

import java.util.*;
import java.util.function.Consumer;

public final class EventBus {
    private final Map<EventType, List<Consumer>> listeners = new HashMap<>();

    public <T> void emit(EventType type, T payload) {
        RuntimeException exception = null;
        for (Consumer listener : listeners.getOrDefault(type, Collections.emptyList())) {
            try {
                //noinspection unchecked
                listener.accept(payload);
            } catch (RuntimeException e) {
                exception = e;
            }
        }
        if (exception != null) {
            throw exception;
        }
    }

    public <T> void subscribe(EventType type, Consumer<T> listener) {
        listeners.computeIfAbsent(type, __ -> new ArrayList<>()).add(listener);
    }
}

