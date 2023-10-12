package org.goose.level;

import com.raylib.java.Config;
import com.raylib.java.core.Color;
import com.raylib.java.core.input.Keyboard;
import com.raylib.java.core.rCore;
import com.raylib.java.raymath.Vector2;
import org.goose.Main;
import org.goose.core.Input;
import org.goose.core.Physics;
import org.goose.core.Renderer;
import org.goose.core.event.core.EventHandler;
import org.goose.core.event.core.EventListener;
import org.goose.core.event.core.EventManager;
import org.goose.core.event.core.ListenerPriority;
import org.goose.core.event.events.core.RenderDrawEvent;
import org.goose.core.event.events.gui.TextButtonClickEvent;
import org.goose.core.event.events.core.TickEvent;
import org.goose.core.gui.GuiHandler;
import org.goose.core.gui.elements.TextButton;
import org.goose.core.gui.elements.TextLabel;
import org.goose.core.sound.Audio;
import org.goose.level.PongGame.Pong;
import org.goose.objects.Player;
import org.goose.objects.SFACube;
import org.goose.objects.Tile;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import static org.goose.Main.world;

public class MenuScreen extends Level {

    private final TextButton pongButton = new TextButton("Play Pong", 300, 100, 30, new Vector2(500,500), Color.WHITE, Color.PURPLE);
    private final TextButton platformerButton = new TextButton("Play Platformer", 300, 100, 30, new Vector2(500,600), Color.WHITE, Color.PURPLE);
    private final TextButton closeButton = new TextButton("Close", 100,100, 30, new Vector2(0,0), Color.WHITE, Color.RED);
    private final Audio collisionSound = new Audio("sound/splat.ogg");
    private final ArrayList<SFACube> cubeList = new ArrayList<>();

    public void startGame(){
        world.loadWorldFromCSV("levels/TestMap.csv");
        world.setEnabled(true);
        Player player = new Player(100,100);
        world.entities.add(player);
        this.setEnabled(false);
    }

    public MenuScreen() {
        setBackGroundColor(Color.BLACK);
        pongButton.setTransparency(255/2);
        platformerButton.setTransparency(255/2);
        closeButton.setTransparency(0);
        GuiHandler guiHandler = new GuiHandler(this);
        guiHandler.addElement(pongButton);
        guiHandler.addElement(platformerButton);
        guiHandler.addElement(closeButton);
        int x = 0;
        int y = 0;
        for (int i = 0; i < 25; i++) {
            SFACube cube = new SFACube(x,y,64, this);
            x+=64;
            y+=64;
            if (y + cube.getSizeY() > Renderer.getWindowHeight()) {
                y=0;
            }
            cube.setVelocity(2, 2);
            cubeList.add(cube);
        }
    }

    @EventHandler(priority = ListenerPriority.CRITICAL)
    public void render(RenderDrawEvent event) {
        Renderer.renderer.core.ClearBackground(getBackGroundColor());
        int centerX = (Renderer.getWindowWidth()/2);
        int centerY = (Renderer.getWindowHeight()/2);

        for (SFACube cube : cubeList) {
            cube.render();
        }
    }

    @EventHandler
    public void buttonPressed(TextButtonClickEvent event) {
        //Test example of how to use an event system
        if (event.getButton().equals(closeButton)) {
            Renderer.close();
        }
        if (event.getButton().equals(pongButton)) {
            Pong pong = new Pong();
            Main.worldList.add(pong);
            pong.setEnabled(true);
            Main.menuScreen.setEnabled(false);
        }
        if (event.getButton().equals(platformerButton)) {
            startGame();
        }
    }

    @EventHandler
    public void tick(TickEvent tickEvent) {
        if (Input.pressedKeys.contains(Keyboard.KEY_F11)) {
            if (rCore.IsWindowFullscreen()) {
                Renderer.renderer.core.SetWindowState(Config.ConfigFlag.FLAG_WINDOW_MAXIMIZED);
            } else {
                Renderer.renderer.core.ToggleFullscreen();
            }
        }

        for (SFACube cube : cubeList) {
            cube.handlePhysics();
        }

        ArrayList<SFACube> handledList = new ArrayList<>();

        for (SFACube cube : cubeList) {
            for (SFACube other : cubeList) {
                if (handledList.contains(other) || handledList.contains(cube)) {
                    continue;
                }
                if (other == cube) {
                    continue;
                }
                if (Physics.rectCollided(cube.getRect(), other.getRect())) {
                    cube.velocity.x = -cube.velocity.x;
                    other.velocity.x = -other.velocity.x;
                    cube.velocity.y = -cube.velocity.y;
                    other.velocity.y = -other.velocity.y;
                    handledList.add(cube);
                    handledList.add(other);
                }
            }
            if (cube.x + cube.getSizeX() > Renderer.getWindowWidth()) {
                cube.velocity.x = -cube.velocity.x;
                collisionSound.play();
            } else if (cube.x < 0) {
                cube.velocity.x = -cube.velocity.x;
                collisionSound.play();
            }
            if (cube.getY() + cube.getSizeY() > Renderer.getWindowHeight()) {
                cube.velocity.y = -cube.velocity.y;
                collisionSound.play();
            } else if (cube.y < 0) {
                cube.velocity.y = -cube.velocity.y;
                collisionSound.play();
            }
        }
    }
}
