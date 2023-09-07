package org.goose.core;

import com.raylib.java.Config;
import com.raylib.java.Raylib;
import com.raylib.java.core.rCore;
import com.raylib.java.core.Color;
import com.raylib.java.core.input.Keyboard;
import com.raylib.java.core.rcamera.Camera2D;
import com.raylib.java.raymath.Vector2;
import org.goose.level.World;
import org.goose.objects.Entity;
import org.goose.objects.Tile;

public class Renderer {

    public static Raylib renderer = new Raylib();
    public static boolean drawFPS = false;
    public static Camera2D camera = new Camera2D();
    public static int targetFPS = 60; //Visual FPS
    public static int targetTPS = 120;

    private static int windowWidth = 1920;
    private static int windowHeight = 1080;

    public static void init() {
        rCore.SetConfigFlags(Config.ConfigFlag.FLAG_FULLSCREEN_MODE);
        renderer.core.InitWindow(windowWidth,windowHeight, "Platformer");
        renderer.core.SetTargetFPS(targetFPS);
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
        for(Entity entity : world.entities) {
            entity.render();
        }
    }

    public static void render(World world, double accumulator) {
        Renderer.renderBegin();

        Renderer.renderWorld(world);

        accumulator = Math.round(accumulator*100)/100d;
        Renderer.renderer.text.DrawText("Accumulator: " + accumulator + ", " + "TargetTPS: " + targetTPS, 0,0,32, Color.RED);


        if (drawFPS) {
            renderer.text.DrawText(""+Time.getDeltaTime(), 100,0, 32, Color.BLACK);
            renderer.text.DrawText("FPS:" + String.format("%.1f", (Time.getDeltaTime()*10)), 0,0,32,Color.BLACK);
        }

        Renderer.renderEnd();
    }

    public static void drawTile(int positionX, int positionY, int size, Color color) {
        renderer.shapes.DrawRectangle(positionX, positionY, size, size, color);
    }

    public static int getWindowWidth() {
        return windowWidth;
    }

    public static void setWindowWidth(int windowWidth) {
        Renderer.windowWidth = windowWidth;
    }

    public static int getWindowHeight() {
        return windowHeight;
    }

    public static void setWindowHeight(int windowHeight) {
        Renderer.windowHeight = windowHeight;
    }
}
