package org.goose.level;

import com.raylib.java.core.Color;
import com.studiohartman.jamepad.ControllerUnpluggedException;

import java.awt.*;
import java.io.IOException;

/**
 * Base class for any world, contains functions like ticking, rendering
 * and allows easy world creation.
 */
public abstract class Level {
    private boolean enabled = false;
    private Color backGroundColor;

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

    public abstract void render(double delta) throws IOException;

    public abstract void tick(double delta);
}
