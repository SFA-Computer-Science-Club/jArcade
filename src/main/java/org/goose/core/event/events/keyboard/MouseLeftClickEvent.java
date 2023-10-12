package org.goose.core.event.events.keyboard;

import com.raylib.java.raymath.Vector2;
import org.goose.core.Input;
import org.goose.core.Time;
import org.goose.core.event.events.Event;

public class MouseLeftClickEvent extends Event {
    private Vector2 position;

    public MouseLeftClickEvent() {
        position = Input.getMousePosition();
    }

    public Vector2 getPosition() {
        return position;
    }
}
