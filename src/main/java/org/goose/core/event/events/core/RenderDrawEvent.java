package org.goose.core.event.events.core;

import org.goose.core.Time;
import org.goose.core.event.events.Event;

/**
 * Called when everything should be drawn to the screen
 */
public class RenderDrawEvent extends Event {
    private double time;

    public RenderDrawEvent() {
        time = Time.now();
    }
}
