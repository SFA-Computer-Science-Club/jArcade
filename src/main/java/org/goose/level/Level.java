package org.goose.level;

import java.io.IOException;

/**
 * Base class for any world, contains functions like ticking, rendering
 * and allows easy world creation.
 */
public abstract class Level {
    private boolean enabled = false;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public abstract void render(double delta) throws IOException;

    public abstract void tick(double delta);
}
