package org.goose.core.gui;

import com.raylib.java.core.Color;
import com.raylib.java.raymath.Vector2;
import com.raylib.java.shapes.Rectangle;
import com.raylib.java.text.rText;
import com.raylib.java.shapes.rShapes;
import org.goose.core.Renderer;

public class TextLabel extends Element{
    public Rectangle rect;
    private String text;
    private int fontSize;
    private int transparency = 255;
    private boolean wordWrap = true;
    private Color backgroundColor;
    private Color textColor;

    /**
     * Draws a textbox within a rectangle with automatic scaling of text
     */
    public static final int MEASURE_STATE = 0;
    public static final int DRAW_STATE = 1;

    public static void DrawTextBoxRestricted(Rectangle rect, int fontSize, String text, TextLabel label) {
        int textLength = rText.TextLength(text);
        float textOffsetX = 0f;
        float textOffsetY = 0f;

        float scaleFactor = fontSize/(float)rText.GetFontDefault().baseSize;

        int state = label.isWordWrap() ? MEASURE_STATE : DRAW_STATE;

        int startLine = -1;
        int endLine = -1;
        int lastK = -1;

        for (int i =0, k=0; i<textLength; i++, k++) {
            int codepointByteCount = 0;
            char[] chars = {text.charAt(i)};
            int codepoint = rText.GetCodepoint(chars, codepointByteCount);

            int index = rText.GetGlyphIndex(rText.GetFontDefault(), codepoint);

            if (codepoint == 0x3f) {
                codepointByteCount=1;
            }

            float glyphWidth = 0f;
            if (codepoint != '\n') {
                glyphWidth = (rText.GetFontDefault().glyphs[index].advanceX == 0) ? rText.GetFontDefault().recs[index].width*scaleFactor : rText.GetFontDefault().glyphs[index].advanceX*scaleFactor;

                if (i+1 < textLength) {
                    glyphWidth = glyphWidth + 1; //TODO If using spacing, implement variable and replace 0 with it. Not needed right now
                }
            }
            if (state == MEASURE_STATE) {
                if ((codepoint == ' ') || (codepoint == '\t') || (codepoint == '\n')) {
                    endLine = i;
                }
                if ((textOffsetX + glyphWidth) > rect.getWidth()) {
                    endLine = (endLine < 1) ? i : endLine;
                    if (i == endLine) {
                        endLine -= codepointByteCount;
                    }
                    if ((startLine + codepointByteCount) == endLine) {
                        endLine = (i-codepointByteCount);
                    }
                    state = DRAW_STATE;
                } else if ((i+1) == textLength) {
                    endLine = i;
                    state = DRAW_STATE;
                } else if (codepoint == '\n') {
                    state = DRAW_STATE;
                }
                if (state == DRAW_STATE) {
                    textOffsetX = 0;
                    i = startLine;
                    glyphWidth = 0;

                    int tmp = lastK;
                    lastK = k-1;
                    k = tmp;
                }
            } else {
                if (codepoint == '\n') {
                    if (!label.isWordWrap()) {
                        textOffsetY += (rText.GetFontDefault().baseSize + rText.GetFontDefault().baseSize/2.0f)*scaleFactor;
                        textOffsetX = 0;
                    }
                } else {
                    if (!label.isWordWrap() && ((textOffsetX + glyphWidth) > rect.getWidth())) {
                        textOffsetY += (rText.GetFontDefault().baseSize + rText.GetFontDefault().baseSize/2.0f)*scaleFactor;
                        textOffsetX = 0;
                    }

                    if ((textOffsetY + rText.GetFontDefault().baseSize*scaleFactor) > rect.getHeight()) {
                        break;
                    }


                    if ((codepoint != ' ') && (codepoint != '\t')) {
                        Renderer.renderer.text.DrawTextCodepoint(rText.GetFontDefault(), codepoint, new Vector2(rect.x + textOffsetX , rect.y + textOffsetY), fontSize, label.textColor);
                    }
                }
                if (label.wordWrap && (i == endLine)) {
                    textOffsetY += (rText.GetFontDefault().baseSize + rText.GetFontDefault().baseSize/2.0f) * scaleFactor;
                    textOffsetX = 0;
                    startLine = endLine;
                    endLine = -1;
                    glyphWidth = 0;
                    k = lastK;

                    state = MEASURE_STATE;
                }

            }
            textOffsetX += glyphWidth;
        }
    }

    public TextLabel(String text, int width, int height, int fontSize, Vector2 position, Color backgroundColor, Color textColor) {
        this.setPosition(position);
        this.text = text;
        this.fontSize = fontSize;
        this.rect = new Rectangle(position.x, position.y, width,height);
        this.backgroundColor = new Color(backgroundColor.r, backgroundColor.g, backgroundColor.b, 255);
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

    public boolean isWordWrap() {
        return wordWrap;
    }

    public void setWordWrap(boolean wordWrap) {
        this.wordWrap = wordWrap;
    }

    public int getTransparency() {
        return transparency;
    }

    public void setTransparency(int transparency) {
        this.transparency = transparency;
        this.backgroundColor.setA((transparency/100) * 255);
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    @Override
    public void render(double deltaTime) {
        rShapes.DrawRectangleRec(rect, backgroundColor);
        DrawTextBoxRestricted(rect, fontSize, text, this);
    }
}
