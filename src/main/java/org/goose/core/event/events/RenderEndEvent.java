package org.goose.core.event.events;

import org.goose.core.Time;

public class RenderEndEvent extends Event{
    private double time;

    public RenderEndEvent() {
        time = Time.now();
    }
}
