package org.goose.core.event.events;

import org.goose.core.Time;
import org.goose.core.event.core.EventManager;
import org.goose.core.event.core.ListenerPriority;

/**
 * Abstract event class, used in the other events
 */
public abstract class Event {
    private boolean cancelled;
    private double time = Time.now();

    public void dispatch() {
        //For every value in the ListenerPriority, fire its associated priority
        for (int i = 0; i < ListenerPriority.values().length; i++) {
            EventManager.dispatchEvent(this, ListenerPriority.values()[i]);
        }
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public double getTime() {
        return this.time;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
