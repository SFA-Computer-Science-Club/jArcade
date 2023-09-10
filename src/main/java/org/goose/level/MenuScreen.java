package org.goose.level;

import com.raylib.java.core.Color;
import com.raylib.java.raymath.Vector2;
import com.raylib.java.shapes.Rectangle;
import org.goose.core.Input;
import org.goose.core.Physics;
import org.goose.core.Renderer;
import org.goose.objects.Player;
import com.raylib.java.text.rText;

import java.io.IOException;

import static org.goose.Main.world;

public class MenuScreen extends Level{

    public void startGame() throws IOException {
        world.loadWorldFromCSV("levels/TestMap.csv");
        world.setEnabled(true);
        Player player = new Player(100,100);
        world.entities.add(player);
        this.setEnabled(false);
    }

    @Override
    public void render(double delta) throws IOException {
        int centerX = (Renderer.getWindowWidth()/2);
        int centerY = (Renderer.getWindowHeight()/2);

        Vector2 textSize = rText.MeasureTextEx(rText.GetFontDefault(), "SFA Platformer Menu", 50, 1);

        Renderer.renderer.text.DrawText("SFA Platformer Menu", centerX-((int)textSize.x/2),centerY-300, 50, Color.DARKPURPLE);

        Rectangle rect = new Rectangle(new Vector2((Renderer.getWindowWidth()/2f)-125, (Renderer.getWindowHeight()/2f) -50), 250,100);

        if (Physics.pointCollidingRect(rect, Input.getMousePosition())) {
            Renderer.renderer.shapes.DrawRectangle((int)rect.x,(int)rect.y, (int)rect.width, (int)rect.height, Color.DARKPURPLE);
            Renderer.renderer.text.DrawText("Left Click to Start", (int) rect.x+10, (int) rect.y+30, 25, Color.WHITE);

            if (Input.isLeftMouseClicked()) {
                startGame();
            }
        } else {
            Renderer.renderer.shapes.DrawRectangle((int)rect.x,(int)rect.y, (int)rect.width, (int)rect.height, Color.DARKPURPLE);
            Renderer.renderer.text.DrawText("Start: World", (int) rect.x+50, (int) rect.y+30, 25, Color.WHITE);
        }

    }

    @Override
    public void tick(double delta) {

    }
}