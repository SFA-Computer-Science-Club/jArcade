package org.goose.core.gui;

import com.raylib.java.core.Color;
import com.raylib.java.core.input.Keyboard;
import com.raylib.java.raymath.Vector2;
import com.raylib.java.shapes.rShapes;
import org.goose.core.Input;
import org.goose.core.Physics;
import org.goose.core.Renderer;

import java.util.ArrayList;

/**
 * GUI Element allowing text input
 */
public class TextBox extends TextLabel {

    private boolean selected = false;

    public TextBox(String text, int width, int height, int fontSize, Vector2 position, Color backgroundColor, Color textColor) {
        super(text, width, height, fontSize, position, backgroundColor, textColor);
    }

    public String getKeyPressed() {
        String out = super.getText();
        ArrayList<Integer> pressedChars = Input.charPressedKeys;

        if (Input.renderPressedKeys.contains(Keyboard.KEY_BACKSPACE)) {
            if (!out.isEmpty() || out != "") {
                out = out.substring(0, out.length()-1);
            }
        }
        for (int key : pressedChars) {
            if (key != 0) {
                out += (char)key;
            }
        }
        return out;
    }

    @Override
    public void render(double deltaTime) {
        boolean hoveredOver = Physics.pointCollidingRect(super.rect, Input.getMousePosition());
        if (hoveredOver) {
            Color darkerShade = new Color((int) (getBackgroundColor().getR() * .7), (int) (getBackgroundColor().getG() * .7), (int) (getBackgroundColor().getB() * .7), getBackgroundColor().getA());
            rShapes.DrawRectangleRec(super.rect, darkerShade);
            if (Input.isLeftMouseClicked()) {
                selected = true;
            }
        } else {
            if (Input.isLeftMouseClicked()) {
                selected = false;
            }
            rShapes.DrawRectangleRec(super.rect, super.getBackgroundColor());
        }

        if (selected) {
            super.setText(getKeyPressed());
        }
        TextLabel.DrawTextBoxRestricted(super.rect, super.getFontSize(), super.getText(), this);
    }
}
