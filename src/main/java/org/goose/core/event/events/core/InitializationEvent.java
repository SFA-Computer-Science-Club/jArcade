package org.goose.core.event.events.core;

import org.goose.core.event.core.EventManager;
import org.goose.core.event.events.Event;

public class InitializationEvent extends Event {
    /**
     * This automatically invokes itself, call it BEFORE you do the game loop
     */
    public InitializationEvent() {
        this.dispatch();
    }
}
