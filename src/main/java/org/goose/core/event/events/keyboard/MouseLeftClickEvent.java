package org.goose.core.event.events.keyboard;

import com.raylib.java.raymath.Vector2;
import org.goose.core.Input;
import org.goose.core.Time;
import org.goose.core.event.events.Event;

public class MouseLeftClickEvent extends Event {
    private double time;
    private Vector2 position;

    public MouseLeftClickEvent() {
        time = Time.now();
        position = Input.getMousePosition();
    }

    public double getTime() {
        return time;
    }

    public Vector2 getPosition() {
        return position;
    }
}
