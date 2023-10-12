package org.goose.core.event.events.keyboard;

import org.goose.core.Time;
import org.goose.core.event.events.Event;

import java.util.ArrayList;

public class KeyboardInputEvent extends Event {
    private ArrayList<Integer> pressedKeys;

    public KeyboardInputEvent() {

    }

    public ArrayList<Integer> getPressedKeys() {
        return pressedKeys;
    }

    public void setPressedKeys(ArrayList<Integer> pressedKeys) {
        this.pressedKeys = pressedKeys;
    }

    public boolean keyPressed(int key) {
        return this.pressedKeys.contains(key);
    }
}
