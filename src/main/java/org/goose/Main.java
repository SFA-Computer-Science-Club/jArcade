package org.goose;


import com.raylib.java.textures.Image;
import org.goose.core.*;
import org.goose.level.Level;
import org.goose.level.MenuScreen;
import org.goose.level.World;
import org.goose.objects.DirtBlock;
import org.goose.objects.GrassBlock;

import java.io.IOException;
import java.util.ArrayList;


public class Main {

    public static World world = new World();
    public static MenuScreen menuScreen = new MenuScreen();

    public static ArrayList<Level> worldList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        Renderer.init();

        //Go through and initialize our textures
        worldList.add(world);
        worldList.add(menuScreen);

        //init menu
        menuScreen.setEnabled(true);

        DirtBlock.initTexture();
        GrassBlock.initTexture();

        Image image = TextureLoader.loadImage("textures/dirt_block.png");
        Renderer.renderer.core.SetWindowIcon(image);
        Time.setLastTime(Time.now());

        double accumulator = 0.0;
        double lastUpdateTime = Time.now();

        //main game loop
        while (!Renderer.shouldClose()) {
            double deltaTime = (Time.now()-lastUpdateTime);
            lastUpdateTime += deltaTime;

            double targetTPS = (1000d/Renderer.targetTPS);

            accumulator += deltaTime;

            Input.registerInput(); //Gather input for processing in tick

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