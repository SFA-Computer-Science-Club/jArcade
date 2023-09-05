package org.goose;


import com.raylib.java.Raylib;
import com.raylib.java.core.Color;
import com.raylib.java.core.input.Keyboard;
import com.raylib.java.core.rcamera.Camera2D;
import com.raylib.java.raymath.Vector2;
import com.raylib.java.textures.Image;
import com.raylib.java.textures.Texture2D;
import org.goose.core.*;
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
        Renderer.renderer.core.SetWindowIcon(image);

        //main game loop
        while (!Renderer.shouldClose()) {
            Time.setCurrentTime(Time.now());

            Physics.tick(Time.getDeltaTime()); //Calculate physics, movement, AI etc
            Renderer.render(world); //Update everything

            Time.sleep(Time.getCurrentTime() + (1000f/Renderer.targetFPS) - Time.now());
            Time.setLastTime(Time.now());
        }
    }
}