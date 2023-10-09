package org.goose.core.event.events.core;

import org.goose.core.Time;
import org.goose.core.event.events.Event;

public class CloseEvent extends Event {
    private double time;
    public CloseEvent() {
        time = Time.now();
    }

    public double getTime() {
        return time;
    }
}
