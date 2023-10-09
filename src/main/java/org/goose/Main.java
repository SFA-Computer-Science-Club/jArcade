package org.goose;

import org.goose.core.*;
import org.goose.core.event.core.EventHandler;
import org.goose.core.event.core.EventManager;
import org.goose.core.event.events.core.InitializationEvent;
import org.goose.core.gui.GuiHandler;
import org.goose.core.sound.Audio;
import org.goose.level.*;
import org.goose.level.PlatformerGame.World;

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

        //init menu
        menuScreen.setEnabled(true);

        //main game loop
        while (!Renderer.shouldClose()) {
            Renderer.startFrame();

            //Fires at exact intervals, will halt the render process if ticking too long
            //Stable enough to be used with multiplayer\
            while (Renderer.canTick()) {
                Renderer.tick();
            }
            //You should implement your draw calls here
            Renderer.drawFrame();
            //Fires whenever the current frame ends
            Renderer.endFrame();
        }
        //Fires an event upon closing the game
        Renderer.close();
    }
}