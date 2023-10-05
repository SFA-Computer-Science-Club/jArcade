package org.goose.core.event.events;

import org.goose.core.Time;

public class RenderBeginEvent extends Event{
    private double time;

    public RenderBeginEvent() {
        time = Time.now();
    }
}
