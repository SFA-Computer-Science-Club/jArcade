package org.goose.level;

import com.raylib.java.core.Color;
import com.raylib.java.core.input.Keyboard;
import com.raylib.java.core.rCore;
import com.raylib.java.raymath.Vector2;
import com.raylib.java.shapes.Rectangle;
import org.goose.Main;
import org.goose.core.Input;
import org.goose.core.Renderer;
import org.goose.objects.DirtBlock;
import org.goose.objects.Entity;
import org.goose.objects.GrassBlock;
import org.goose.objects.Tile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class World extends Level{
    public HashMap<Vector2, Tile> tileMap = new HashMap<>();

    public ArrayList<Entity> entities = new ArrayList<>(); //holds all players, or entities

    private Color backGroundColor = Color.GRAY;

    public Color getBackGroundColor() {
        return backGroundColor;
    }

    public void setBackGroundColor(Color backGroundColor) {
        this.backGroundColor = backGroundColor;
    }

    private double gravity = 25/100d;

    public double getGravity() {
        return gravity;
    }

    public void setGravity(double gravity) {
        this.gravity = gravity;
    }

    public World() {

    }

    @Override
    public void render(double delta) {

        //goes through all of the objects and renders them
        for (Vector2 vector : this.tileMap.keySet()) {
            Tile tile = this.tileMap.get(vector);
            tile.render();
        }

        //after map is rendered, render player/entities
        for(Entity entity : this.entities) {
            entity.render();
        }

        Renderer.renderer.text.DrawText("FPS: " + rCore.GetFPS(), 0,0, 30, Color.RED);
        Renderer.renderer.text.DrawText("Player Velocity: " + Main.world.entities.get(0).getVelocity().x + ", " + Main.world.entities.get(0).getVelocity().y, 0,40, 30, Color.YELLOW);
        Renderer.renderer.text.DrawText("Mouse Position: " + Input.getMousePosition().x + "," + Input.getMousePosition().y, 0, 80, 30, Color.GREEN);

        com.raylib.java.shapes.Rectangle rectangle = new Rectangle(Input.getMousePosition().x, Input.getMousePosition().y, 25,25);
        for (Vector2 vector2 : Main.world.tileMap.keySet()) {
            Tile tile = Main.world.tileMap.get(vector2);
            if (Renderer.renderer.shapes.CheckCollisionRecs(tile.getRect(), rectangle)) {
                Renderer.renderer.shapes.DrawRectangle((int) rectangle.x, (int) rectangle.y, (int) rectangle.width, (int) rectangle.height, Color.RED);
                break;
            } else {
                Renderer.renderer.shapes.DrawRectangle((int) rectangle.x, (int) rectangle.y, (int) rectangle.width, (int) rectangle.height, Color.BLUE);
            }
        }
    }

    @Override
    public void tick(double deltaTime) {
        for (Entity entity : entities) {
            entity.tick(deltaTime);
        }

        if (Input.heldKeys.contains(Keyboard.KEY_RIGHT)) {
            Renderer.camera.target.x -= 1;
        }
        if (Input.heldKeys.contains(Keyboard.KEY_LEFT)) {
            Renderer.camera.target.x += 1;
        }
    }

    

    public void loadWorldFromCSV(String path) throws IOException {
        //clear old map and load the new map, allows a world to have map changed mid-game
        tileMap.clear();

        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(path)) {
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream));

            //read through the csv
            int counter = 0;
            String line;

            int width = Renderer.getWindowWidth();
            int height = Renderer.getWindowHeight();

            while ((line = fileReader.readLine()) != null) {

                //dont mind the code below, I prefer working with arraylists
                ArrayList<String> stringList = new ArrayList<>(Arrays.asList(line.split(",")));

                int horizontalCounter = 0;
                for(String object : stringList) {
                    //this will be every tile on each line
                    int tileNumber = Integer.parseInt(object);
                    if (tileNumber == 3) {
                        tileMap.put(new Vector2(horizontalCounter, counter), new DirtBlock(horizontalCounter * (width/32), counter * (width/32),width/32));
                    } else if (tileNumber == 6) {
                        tileMap.put(new Vector2(horizontalCounter, counter), new GrassBlock(horizontalCounter * (width/32), counter * (width/32),width/32));
                    }
                    horizontalCounter++;
                }
                counter++;
            }
        }
    }
}
