package org.goose.core;

import com.raylib.java.core.Color;
import com.raylib.java.core.input.Keyboard;
import com.raylib.java.raymath.Vector2;
import com.raylib.java.shapes.Rectangle;
import org.goose.Main;
import org.goose.objects.Entity;
import org.goose.objects.Tile;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Physics {

    public static void tick(double deltaTime) {
        //pass physics to world, world passes to entities etc
        if (Input.isKeyPressed(Keyboard.KEY_F3)) {
            Renderer.drawFPS = !Renderer.drawFPS;
        }

        Main.world.tick(deltaTime);
    }

    //collision related mechanics
    public static ArrayList<Tile> entityCollided(Entity entity) {
        //returns all things that an entity is colliding with (player/mob/whatever)
        ArrayList<Tile> collided = new ArrayList<>();
        for (Vector2 vector2 : Main.world.tileMap.keySet()) {
            Tile tile = Main.world.tileMap.get(vector2);
            if (Renderer.renderer.shapes.CheckCollisionRecs(tile.getRect(), entity.getRect())) {
                collided.add(tile);
            }
        }
        return collided;
    }

    public static ArrayList<Tile> entityCollided(Rectangle rectangle) {
        //returns all things that an entity is colliding with (player/mob/whatever)
        ArrayList<Tile> collided = new ArrayList<>();
        for (Vector2 vector2 : Main.world.tileMap.keySet()) {
            Tile tile = Main.world.tileMap.get(vector2);
            if (Renderer.renderer.shapes.CheckCollisionRecs(tile.getRect(), rectangle)) {
                collided.add(tile);
            }
        }
        return collided;
    }
}
