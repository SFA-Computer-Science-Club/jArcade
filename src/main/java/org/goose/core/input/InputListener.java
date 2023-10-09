package org.goose.core.input;

import org.goose.core.Renderer;
import org.goose.core.event.core.EventHandler;
import org.goose.core.event.core.EventListener;
import org.goose.core.event.core.EventManager;
import org.goose.core.event.core.ListenerPriority;
import org.goose.core.event.events.core.RenderBeginEvent;
import org.goose.core.event.events.keyboard.MouseLeftClickEvent;

public class InputListener implements EventListener {
    public InputListener() {
        EventManager.addListener(this);
    }

    @EventHandler(priority = ListenerPriority.CRITICAL)
    public void renderKeyCheck(RenderBeginEvent event) {
        if (Renderer.renderer.core.IsMouseButtonReleased(0)) {
            MouseLeftClickEvent mouseLeftClickEvent = new MouseLeftClickEvent();
            mouseLeftClickEvent.dispatch();
        }
    }
}
