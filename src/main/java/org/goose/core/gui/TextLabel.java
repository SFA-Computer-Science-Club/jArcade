package org.goose.core.gui;

import com.raylib.java.core.Color;
import com.raylib.java.raymath.Vector2;
import com.raylib.java.shapes.Rectangle;
import com.raylib.java.text.rText;
import org.goose.core.Renderer;

public class TextLabel extends Element{
    public Rectangle rect;
    private String text;
    private int fontSize;
    private Color backgroundColor;
    private Color textColor;

    /**
     * Returns new font size scaled down to fit within the bounds of the rectangle
     */
    public static int FitTextToRect(Rectangle rect, int fontSize) {
        return 1;
    }

    public TextLabel(String text, int width, int height, int fontSize, Vector2 position, Color backgroundColor, Color textColor) {
        this.setPosition(position);
        this.text = text;
        this.fontSize = fontSize;
        this.rect = new Rectangle(position.x, position.y, width,height);
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Color getTextColor() {
        return textColor;
    }

    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void render(double deltaTime) {
        Renderer.renderer.shapes.DrawRectangle((int) getPosition().x, (int) getPosition().y, (int) rect.width, (int) rect.height, backgroundColor);

        Vector2 textSize = rText.MeasureTextEx(rText.GetFontDefault(), "xxx.x, Rect Width: xxx.x", fontSize, 0);

        if (textSize.x < rect.getWidth()) {
            Renderer.renderer.text.DrawText(textSize.x +", Rect Width: " + rect.getWidth(), (int) (rect.x), (int) (rect.y), fontSize, textColor);
        } else {
            Renderer.renderer.text.DrawText(textSize.x +", Rect Width: " + rect.getWidth(), (int) (rect.x), (int) (rect.y), fontSize, textColor);
        }

    }
}
