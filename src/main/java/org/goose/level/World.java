package org.goose.level;

import com.raylib.java.core.Color;
import com.raylib.java.raymath.Vector2;
import org.goose.Main;
import org.goose.objects.DirtBlock;
import org.goose.objects.Entity;
import org.goose.objects.GrassBlock;
import org.goose.objects.Tile;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class World {
    public HashMap<Vector2, Tile> tileMap = new HashMap<>();

    public ArrayList<Entity> entities = new ArrayList<>(); //holds all players, or entities

    private Color backGroundColor = Color.GRAY;

    public Color getBackGroundColor() {
        return backGroundColor;
    }

    public void setBackGroundColor(Color backGroundColor) {
        this.backGroundColor = backGroundColor;
    }

    private double gravity = -9.807;

    public double getGravity() {
        return gravity;
    }

    public void setGravity(double gravity) {
        this.gravity = gravity;
    }

    public World() {

    }

    

    public void loadWorldFromCSV(String path) throws IOException {
        //clear old map and load the new map, allows a world to have map changed mid-game
        tileMap.clear();

        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(path)) {
            System.out.println(Main.class.getClassLoader().getResource(path).getPath());
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream));

            //read through the csv
            int counter = 0;
            String line;
            while ((line = fileReader.readLine()) != null) {

                //dont mind the code below, I prefer working with arraylists
                ArrayList<String> stringList = new ArrayList<>(Arrays.asList(line.split(",")));

                int horizontalCounter = 0;
                for(String object : stringList) {
                    //this will be every tile on each line
                    int tileNumber = Integer.parseInt(object);

                    if (tileNumber == 3) {
                        tileMap.put(new Vector2(horizontalCounter, counter), new DirtBlock(horizontalCounter * 32, counter * 32,32));
                    } else if (tileNumber == 6) {
                        tileMap.put(new Vector2(horizontalCounter, counter), new GrassBlock(horizontalCounter * 32, counter * 32,32));
                    }
                    horizontalCounter++;
                }
                counter++;
            }
        }
    }
}
