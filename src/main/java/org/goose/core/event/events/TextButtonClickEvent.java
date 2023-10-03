package org.goose.core.event.events;

import org.goose.core.gui.elements.TextButton;
import org.goose.level.Level;

public class TextButtonClickEvent extends Event{

    private TextButton button;
    private Level level;

    public TextButtonClickEvent(TextButton button, Level currentLevel) {
        this.button = button;
        this.level = currentLevel;
    }
}
