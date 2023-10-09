package org.goose.core.event.events.core;

import org.goose.core.Time;
import org.goose.core.event.events.Event;

public class RenderBeginEvent extends Event {
    private double time;

    public RenderBeginEvent() {
        time = Time.now();
    }
}
