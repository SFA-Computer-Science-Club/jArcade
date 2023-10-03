package org.goose.core.gui.elements;

import com.raylib.java.core.Color;
import com.raylib.java.core.input.Keyboard;
import com.raylib.java.raymath.Vector2;
import com.raylib.java.shapes.rShapes;
import org.goose.core.Input;
import org.goose.core.Physics;
import org.goose.core.Time;

import java.util.ArrayList;

/**
 * GUI Element allowing text input
 */
public class TextBox extends TextLabel {

    private boolean selected = false;

    public TextBox(String text, int width, int height, int fontSize, Vector2 position, Color backgroundColor, Color textColor) {
        super(text, width, height, fontSize, position, backgroundColor, textColor);
    }

    private int counter = 0;
    private double lastDeleteTime = Time.getLastTime();
    public String getKeyPressed() {
        counter += 1;
        String out = super.getText();
        ArrayList<Integer> pressedChars = Input.charPressedKeys;

        if (Input.renderPressedKeys.contains(Keyboard.KEY_ENTER)) {
            out += "\n";
        }
        if (Input.renderPressedKeys.contains(Keyboard.KEY_BACKSPACE)) {
            if (!out.isEmpty() || out != "") {
                out = out.substring(0, out.length()-1);
            }
            lastDeleteTime = Time.now();
        } else if (Input.isKeyDown(Keyboard.KEY_BACKSPACE) && ((Time.now()-lastDeleteTime) >= 100)) {
            if (!out.isEmpty() || out != "") {
                out = out.substring(0, out.length() - 1);
            }
            lastDeleteTime = Time.now();
        }

        for (int key : pressedChars) {
            if (key != 0) {
                out += (char)key;
            }
        }
        return out;
    }

    private int flasher = 0;

    @Override
    public void render(double deltaTime) {
        flasher += 1;
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
            if (flasher > 60 || flasher < 15) {
                if (flasher > 60) {
                    flasher = 0;
                }
                TextLabel.DrawTextBoxRestricted(super.rect, super.getFontSize(), super.getText() + "_", this);
            } else {
                TextLabel.DrawTextBoxRestricted(super.rect, super.getFontSize(), super.getText(), this);
            }
        } else {
            TextLabel.DrawTextBoxRestricted(super.rect, super.getFontSize(), super.getText(), this);
        }
    }
}
