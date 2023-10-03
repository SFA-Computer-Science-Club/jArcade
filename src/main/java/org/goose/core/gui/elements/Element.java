package org.goose.core.gui.elements;

import com.raylib.java.raymath.Vector2;

/**
 * Base class for GUI elements
 */
public abstract class Element {
    private Vector2 position = new Vector2();
    private boolean visible = false;
    public abstract void render(double deltaTime);
    public enum TextCenter {
        TOP_LEFT,
        TOP_CENTER,
        TOP_RIGHT,
        MIDDLE_LEFT,
        MIDDLE_CENTER,
        MIDDLE_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_MIDDLE,
        BOTTOM_RIGHT
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }
}
