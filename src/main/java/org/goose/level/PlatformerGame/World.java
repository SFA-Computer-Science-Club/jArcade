package org.goose.level.PlatformerGame;

import com.raylib.java.Config;
import com.raylib.java.core.Color;
import com.raylib.java.core.input.Keyboard;
import com.raylib.java.core.rCore;
import com.raylib.java.raymath.Vector2;
import org.goose.Main;
import org.goose.core.Input;
import org.goose.core.Renderer;
import org.goose.core.event.core.EventHandler;
import org.goose.core.event.events.core.RenderDrawEvent;
import org.goose.core.event.events.core.TickEvent;
import org.goose.core.gui.elements.CheckBox;
import org.goose.level.Level;
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

public class World extends Level {
    public HashMap<Vector2, Tile> tileMap = new HashMap<>();
    public boolean devMode = false;

    public ArrayList<Entity> entities = new ArrayList<>(); //holds all players, or entities

    CheckBox checkBox = new CheckBox("Gravity", 300,125,30, new Vector2(0,0), Color.WHITE, Color.BLACK);

    private double gravity = 25/100d;

    public double getGravity() {
        return gravity;
    }

    public void setGravity(double gravity) {
        this.gravity = gravity;
    }

    public World() {
        checkBox.setTransparency(1);
        setBackGroundColor(Color.GRAY);
    }

    @EventHandler
    public void render(RenderDrawEvent event) {
        Renderer.renderer.core.ClearBackground(getBackGroundColor());

        //goes through all of the objects and renders them
        for (Vector2 vector : this.tileMap.keySet()) {
            Tile tile = this.tileMap.get(vector);
            tile.render();
        }

        //after map is rendered, render player/entities
        for(Entity entity : this.entities) {
            entity.render();
        }
    }

    @EventHandler
    public void tick(TickEvent event) {
        if (checkBox.isChecked()) {
            if (gravity != 0) {
                setGravity(0);
            } else {
                gravity = 25/100d;
            }
        }

        for (Entity entity : entities) {
            entity.tick(0);
        }

        if (Input.heldKeys.contains(Keyboard.KEY_RIGHT)) {
            Renderer.camera.target.x -= 1;
        }
        if (Input.heldKeys.contains(Keyboard.KEY_LEFT)) {
            Renderer.camera.target.x += 1;
        }

        if (Input.pressedKeys.contains(Keyboard.KEY_F11)) {
            if (rCore.IsWindowFullscreen()) {
                Renderer.renderer.core.SetWindowState(Config.ConfigFlag.FLAG_WINDOW_MAXIMIZED);
            } else {
                Renderer.renderer.core.ToggleFullscreen();
            }
        }

        if (Input.pressedKeys.contains(Keyboard.KEY_F10)) {
            devMode = !devMode;
        }
    }


    /**
     * Loads world from a CSV file at a path, will probably support string-names instead of blockIDs
     * when loading to improve clarity
     * @param path
     * @throws IOException
     */
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
