package org.goose.level;

import com.raylib.java.core.Color;
import org.goose.core.event.core.EventListener;
import org.goose.core.event.core.EventManager;

/**
 * Base class for any world, contains functions like ticking, rendering
 * and allows easy world creation.
 */
public abstract class Level implements EventListener {
    private boolean enabled = false;
    private Color backGroundColor;

    public Level() {
        EventManager.addListener(this);
    }

    public com.raylib.java.core.Color getBackGroundColor() {
        return backGroundColor;
    }

    public void setBackGroundColor(com.raylib.java.core.Color backGroundColor) {
        this.backGroundColor = backGroundColor;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
