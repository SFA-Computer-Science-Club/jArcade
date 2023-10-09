package org.goose.core.gui.elements;

import com.raylib.java.core.Color;
import com.raylib.java.raymath.Vector2;
import com.raylib.java.shapes.Rectangle;
import com.raylib.java.shapes.rShapes;
import org.goose.core.Input;
import org.goose.core.Physics;
import org.goose.core.Time;
import org.goose.core.event.core.EventHandler;
import org.goose.core.event.events.core.RenderDrawEvent;

public class CheckBox extends TextLabel {
    Rectangle checkRect;
    Rectangle textRect;

    boolean checked = false;

    public CheckBox(String text, int width, int height, int fontSize, Vector2 position, Color backgroundColor, Color textColor) {
        super(text, width, height, fontSize, position, backgroundColor, textColor);
        checkRect = new Rectangle(position.x + (rect.getWidth()/8), position.y + (rect.getHeight()/4), (rect.getWidth()/4), rect.getHeight()/2);
        textRect = new Rectangle(position.x + (rect.getWidth()/2), position.y + (rect.getHeight()/4), rect.getWidth()/2 - (rect.getWidth()/20), rect.getHeight()/2);
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    double lastCheckedTime = Time.now();
    double checkCoolDown = 200; //milliseconds

    @EventHandler
    public void render(RenderDrawEvent event) {
        if (!this.getLevel().isEnabled()) {
            return;
        }
        rShapes.DrawRectangleRec(rect, getBackgroundColor());

        if (Physics.pointCollidingRect(checkRect, Input.getMousePosition())) {
            Color darkerColor = new Color(getTextColor().getR(), getTextColor().getG(), getTextColor().getB(), (int)(getTextColor().getA()*.7));
            rShapes.DrawRectangleRec(checkRect, darkerColor);
            if (Time.now() - lastCheckedTime > checkCoolDown) {
                if (Input.isLeftMouseClicked() && !isChecked()) {
                    setChecked(true);
                    lastCheckedTime = Time.now();
                } else if (Input.isLeftMouseClicked() && isChecked()) {
                    lastCheckedTime = Time.now();
                    setChecked(false);
                }
            }
        } else {
            rShapes.DrawRectangleRec(checkRect, getTextColor());
        }

        if (isChecked()) {
            TextLabel.DrawTextBoxRestricted(checkRect, getFontSize() * 2, "X", this, new Color(getBackgroundColor().getR(), getBackgroundColor().getG(), getBackgroundColor().getB(), 255));
        }

        TextLabel.DrawTextBoxRestricted(textRect, getFontSize(), getText(), this);
    }
}
