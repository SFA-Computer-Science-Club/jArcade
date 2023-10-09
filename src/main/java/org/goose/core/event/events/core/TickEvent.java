package org.goose.core.event.events.core;

import org.goose.core.Time;
import org.goose.core.event.events.Event;

public class TickEvent extends Event {
    private double time;

    public TickEvent() {
        time = Time.now();
    }
}
