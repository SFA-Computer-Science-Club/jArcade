package org.goose.core.gui.elements;

import com.raylib.java.core.Color;
import com.raylib.java.raymath.Vector2;
import com.raylib.java.shapes.rShapes;
import org.goose.core.Input;
import org.goose.core.Physics;
import org.goose.core.event.core.EventHandler;
import org.goose.core.event.events.core.RenderDrawEvent;
import org.goose.core.event.events.gui.TextButtonClickEvent;
import org.goose.core.event.events.keyboard.MouseLeftClickEvent;

public class TextButton extends TextLabel{
    private boolean clicked = false;

    public TextButton(String text, int width, int height, int fontSize, Vector2 position, Color backgroundColor, Color textColor) {
        super(text, width, height, fontSize, position, backgroundColor, textColor);
    }

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    /**
     * Resets button to default mode(off)
     */
    public void reset() {
       this.clicked = false;
    }

    public void render() {
        boolean hovered = Physics.pointCollidingRect(super.rect, Input.getMousePosition());
        if (hovered) {
            if (Input.isLeftMouseClicked()) {
                Color darkerShade = new Color((int) (getBackgroundColor().getR() * .7), (int) (getBackgroundColor().getG() * .7), (int) (getBackgroundColor().getB() * .7), getBackgroundColor().getA());
                rShapes.DrawRectangleRec(super.rect, darkerShade);
                if (clicked) {
                    setClicked(false);
                } else {
                    setClicked(true);
                }
            } else {
                Color darkerShade = new Color((int) (getBackgroundColor().getR() * .9), (int) (getBackgroundColor().getG() * .9), (int) (getBackgroundColor().getB() * .9), getBackgroundColor().getA());
                rShapes.DrawRectangleRec(super.rect, darkerShade);
            }
        } else {
            rShapes.DrawRectangleRec(super.rect, super.getBackgroundColor());
        }

        TextLabel.DrawTextBoxRestricted(super.rect, super.getFontSize(), super.getText(), this);
    }

    @EventHandler
    public void handleClick(MouseLeftClickEvent event) {
        if (Physics.pointCollidingRect(this.rect, event.getPosition()) && this.isVisible()) {
            TextButtonClickEvent textButtonClickEvent = new TextButtonClickEvent(this);
            textButtonClickEvent.dispatch();
        }
    }
}
