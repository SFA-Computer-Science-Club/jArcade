package org.goose.core.event.events.core;

import org.goose.core.Time;
import org.goose.core.event.events.Event;

public class RenderEndEvent extends Event {
    private double time;

    public RenderEndEvent() {
        time = Time.now();
    }
}
