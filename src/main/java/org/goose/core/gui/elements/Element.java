package org.goose.core.gui.elements;

import com.raylib.java.raymath.Vector2;
import org.goose.core.event.core.EventListener;
import org.goose.core.event.core.EventManager;
import org.goose.core.gui.GuiHandler;
import org.goose.level.Level;

/**
 * Base class for GUI elements
 */
public abstract class Element implements EventListener {
    private Vector2 position = new Vector2();
    private boolean visible = true;
    private Level level;
    private int zIndex;
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

    public Element(){
        EventManager.addListener(this);
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

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public int getzIndex() {
        return zIndex;
    }

    public void setzIndex(int zIndex) {
        this.zIndex = zIndex;
    }

    public abstract void render();
}
