package org.goose.core;

import com.raylib.java.Raylib;
import com.raylib.java.core.Color;
import com.raylib.java.core.input.Keyboard;
import com.raylib.java.core.rcamera.Camera2D;
import com.raylib.java.raymath.Vector2;
import org.goose.level.World;
import org.goose.objects.Tile;

public class Renderer {

    public static Raylib renderer = new Raylib();
    public static boolean drawFPS = false;
    public static Camera2D camera = new Camera2D();

    public static void init() {
        renderer.core.InitWindow(800,600, "Platformer");
        renderer.core.SetTargetFPS(144);
        renderer.core.SetExitKey(Keyboard.KEY_ESCAPE);
    }

    public static boolean shouldClose() {
        return renderer.core.WindowShouldClose();
    }

    public static void renderFPS() {
        renderer.text.DrawFPS(0,0);
    }

    public static void renderBegin() {
        renderer.core.BeginDrawing();
        renderer.core.ClearBackground(Color.WHITE);
        renderer.core.BeginMode2D(camera);
    }

    public static void renderEnd() {
        renderer.core.EndMode2D();
        renderer.core.EndDrawing();
    }

    public static void renderWorld(World world) {
        //draw sky first
        renderer.core.ClearBackground(world.getBackGroundColor());

        //goes through all of the objects and renders them
        for (Vector2 vector : world.tileMap.keySet()) {
            Tile tile = world.tileMap.get(vector);
            tile.render();
        }

        //after map is rendered, render player/entities
        //TODO
    }

    public static void drawTile(int positionX, int positionY, int size, Color color) {
        renderer.shapes.DrawRectangle(positionX, positionY, size, size, color);
    }
}
