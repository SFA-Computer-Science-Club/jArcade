package org.goose.core;

import com.raylib.java.core.Color;
import com.raylib.java.core.input.Keyboard;
import com.raylib.java.core.rCore;
import com.raylib.java.raymath.Point;
import com.raylib.java.raymath.Vector2;
import com.raylib.java.shapes.Rectangle;
import com.studiohartman.jamepad.ControllerUnpluggedException;
import org.goose.Main;
import org.goose.level.Level;
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

        for (int i = 0; i < Main.worldList.size(); i++) {
            if (Main.worldList.get(i).isEnabled()) {
                Main.worldList.get(i).tick(deltaTime);
            }
        }
    }

    /**
     * Returns a tile that an entity collided with, checks over tileset provided, returns empty
     * arraylist if nothing is collided
     * @param entity
     * @return
     */

    //TODO Pass in the current tileset so it allows world-independent collision checking
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

    /**
     * Checks if a rectangle collided with any tiles in the provided tileset, returns empty
     * arraylist if no collisions occured
     * @param rectangle
     * @return
     */
    public static ArrayList<Tile> rectCollided(Rectangle rectangle) {
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

    /**
     * Checks if two rects have collided
     * @param rect1
     * @param rect2
     * @return
     */
    public static boolean rectCollided(Rectangle rect1, Rectangle rect2) {
        //returns all things that an entity is colliding with (player/mob/whatever)
        return Renderer.renderer.shapes.CheckCollisionRecs(rect1, rect2);
    }

    /**
     * Check if a vector2 position is colliding with a rect
     * @param rect1
     * @param point
     * @return
     */
    public static boolean pointCollidingRect(Rectangle rect1, Vector2 point) {
        return Renderer.renderer.shapes.CheckCollisionPointRec(point, rect1);
    }
}
