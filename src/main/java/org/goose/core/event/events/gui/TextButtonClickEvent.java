package org.goose.core.event.events.gui;

import org.goose.core.event.events.Event;
import org.goose.core.gui.elements.TextButton;
import org.goose.level.Level;

public class TextButtonClickEvent extends Event {

    private TextButton button;
    private Level level;

    public TextButtonClickEvent(TextButton button) {
        this.button = button;
        this.level = button.getLevel();
    }

    public TextButton getButton() {
        return button;
    }

    public Level getLevel() {
        return level;
    }
}
