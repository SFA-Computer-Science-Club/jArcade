package org.goose;

import com.raylib.java.raudioal.Music;
import com.raylib.java.raudioal.Sound;
import com.raylib.java.utils.FileIO;
import org.goose.core.*;
import org.goose.core.event.core.EventManager;
import org.goose.core.sound.Audio;
import org.goose.core.sound.SoundLoader;
import org.goose.level.*;
import org.goose.level.PlatformerGame.World;
import org.goose.level.PongGame.Pong;

import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static World world = new World();
    public static MenuScreen menuScreen = new MenuScreen();
    public static ArrayList<Level> worldList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        Renderer.init();
        Input.init();

        //Go through and initialize our textures
        worldList.add(world);
        worldList.add(menuScreen);
        EventManager.addListener(menuScreen);
        Audio sound = new Audio("sound/trololo.ogg");

        //init menu
        menuScreen.setEnabled(true);

        double accumulator = 0.0;
        double lastUpdateTime = Time.now();
        //main game loop
        while (!Renderer.shouldClose()) {
            double deltaTime = (Time.now()-lastUpdateTime);
            lastUpdateTime += deltaTime;
            double targetTPS = (1000d/Renderer.targetTPS);
            accumulator += deltaTime;
            Input.registerInput(); //Gather input for processing in tick
            sound.play();
            while (accumulator > targetTPS) {
                Physics.tick(targetTPS); //Calculate physics, movement, AI etc
                accumulator -= targetTPS;
                Input.pressedKeys.clear();
            }

            for (Level level : Main.worldList) {
                if (level.isEnabled()) {
                    Renderer.render(level);
                }
            }
        }
    }
}