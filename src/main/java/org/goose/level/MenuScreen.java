package org.goose.level;

import com.raylib.java.Config;
import com.raylib.java.core.Color;
import com.raylib.java.core.input.Keyboard;
import com.raylib.java.core.rCore;
import com.raylib.java.raymath.Vector2;
import org.goose.Main;
import org.goose.core.Input;
import org.goose.core.Renderer;
import org.goose.core.gui.elements.TextButton;
import org.goose.level.PongGame.Pong;
import org.goose.objects.Player;
import com.raylib.java.text.rText;

import java.io.IOException;

import static org.goose.Main.world;

public class MenuScreen extends Level{

    private TextButton pongButton = new TextButton("Play Pong", 300, 100, 30, new Vector2(500,500), Color.WHITE, Color.BLACK);
    private TextButton platformerButton = new TextButton("Play Platformer", 300, 100, 30, new Vector2(500,600), Color.WHITE, Color.BLACK);
    private TextButton closeButton = new TextButton("Close", 100,100, 30, new Vector2(0,0), Color.WHITE, Color.RED);

    public void startGame() throws IOException {
        world.loadWorldFromCSV("levels/TestMap.csv");
        world.setEnabled(true);
        Player player = new Player(100,100);
        world.entities.add(player);
        this.setEnabled(false);
    }

    public MenuScreen() {
        setBackGroundColor(Color.LIGHTGRAY);
    }

    @Override
    public void render(double delta) throws IOException {
        Renderer.renderer.core.ClearBackground(getBackGroundColor());
        int centerX = (Renderer.getWindowWidth()/2);
        int centerY = (Renderer.getWindowHeight()/2);

        pongButton.render(delta);
        platformerButton.render(delta);
        closeButton.render(delta);
    }

    @Override
    public void tick(double delta) {
        if (Input.pressedKeys.contains(Keyboard.KEY_F11)) {
            if (rCore.IsWindowFullscreen()) {
                Renderer.renderer.core.SetWindowState(Config.ConfigFlag.FLAG_WINDOW_MAXIMIZED);
            } else {
                Renderer.renderer.core.ToggleFullscreen();
            }
        }
        if (closeButton.isClicked()) {
            closeButton.setClicked(false);
            Renderer.renderer.core.CloseWindow();
        }

        if (pongButton.isClicked()) {
            pongButton.setClicked(false);
            Pong pong = new Pong();
            Main.worldList.add(pong);
            this.setEnabled(false);
            pong.setEnabled(true);
        }
    }
}
