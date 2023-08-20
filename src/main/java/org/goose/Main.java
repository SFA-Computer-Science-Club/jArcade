package org.goose;


import com.raylib.java.Raylib;
import com.raylib.java.core.Color;
import com.raylib.java.core.input.Keyboard;
import com.raylib.java.core.rcamera.Camera2D;
import com.raylib.java.raymath.Vector2;
import com.raylib.java.textures.Texture2D;
import org.goose.core.Input;
import org.goose.core.Renderer;
import org.goose.level.World;
import org.goose.objects.DirtBlock;
import org.goose.objects.GrassBlock;
import org.goose.objects.Tile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;


public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        Renderer.init();

        //Go through and initialize our textures

        DirtBlock.initTexture();
        //GrassBlock.initTexture();

        World world = new World();
        world.loadWorldFromCSV("levels/TestMap.csv");

        //main game loop
        while (!Renderer.shouldClose()) {
            Renderer.renderBegin();

            if (Input.isKeyPressed(Keyboard.KEY_F3)) {
                Renderer.drawFPS = !Renderer.drawFPS;
            }

            //on first start you'd run something like the main menu, where you can pick which level

            Renderer.renderWorld(world);

            if (Renderer.drawFPS) {
                Renderer.renderFPS();
            }

            if (Input.isKeyHeld(Keyboard.KEY_A)) {
                //a key is being pressed
                Renderer.camera.offset.x += 10;
            } else if (Input.isKeyHeld(Keyboard.KEY_D)) {
                Renderer.camera.offset.x -= 10;
            }

            Renderer.renderEnd();
        }
    }
}