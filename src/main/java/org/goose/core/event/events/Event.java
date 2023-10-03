package org.goose.core.event.events;

import org.goose.core.event.core.EventManager;
import org.goose.core.event.core.ListenerPriority;

/**
 * Abstract event class, used in the other events
 */
public abstract class Event {
    private boolean cancelled;

    public void dispatch() {
        EventManager.dispatchEvent(this, ListenerPriority.NORMAL);
    }


    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
