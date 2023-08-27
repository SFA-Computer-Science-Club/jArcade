package org.goose;


import com.raylib.java.Raylib;
import com.raylib.java.core.Color;
import com.raylib.java.core.input.Keyboard;
import com.raylib.java.core.rcamera.Camera2D;
import com.raylib.java.raymath.Vector2;
import com.raylib.java.textures.Image;
import com.raylib.java.textures.Texture2D;
import org.goose.core.Input;
import org.goose.core.Renderer;
import org.goose.core.TextureLoader;
import org.goose.level.World;
import org.goose.objects.DirtBlock;
import org.goose.objects.GrassBlock;
import org.goose.objects.Player;
import org.goose.objects.Tile;
import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLOutput;
import java.util.Arrays;


public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        Renderer.init();

        //Go through and initialize our textures

        DirtBlock.initTexture();
        GrassBlock.initTexture();

        World world = new World();
        world.loadWorldFromCSV("levels/TestMap.csv");

        Player player = new Player(100,100);
        world.entities.add(player);


        Image image = TextureLoader.loadImage("textures/dirt_block.png");
        //TextureLoader.setWindowIcon(image); //TODO Figure out fix for window icon crash
        Renderer.renderer.core.SetWindowIcon(image);

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

            //handle player input, for now this will do, but eventually we will move all of the input handling
            // to the world class, so each world can choose what it wants to do (say if you had space invaders or something)
            if (Input.isKeyHeld(Keyboard.KEY_A)) {
                player.getRect().x -= 2;
            } else if (Input.isKeyHeld(Keyboard.KEY_D)) {
                player.getRect().x += 2;
            }

            if (Input.isKeyPressed(Keyboard.KEY_SPACE)) {
                player.getRect().y -= 10;
            }

            Renderer.renderEnd();
        }
    }
}