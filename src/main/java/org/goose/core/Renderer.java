package org.goose.core;

import com.raylib.java.Config;
import com.raylib.java.Raylib;
import com.raylib.java.core.rCore;
import com.raylib.java.core.Color;
import com.raylib.java.core.input.Keyboard;
import com.raylib.java.core.rcamera.Camera2D;
import com.raylib.java.raymath.Vector2;
import com.raylib.java.shapes.Rectangle;
import org.goose.Main;
import org.goose.level.World;
import org.goose.objects.Entity;
import org.goose.objects.Tile;

public class Renderer {

    public static Raylib renderer = new Raylib();
    public static boolean drawFPS = false;
    public static Camera2D camera = new Camera2D();
    public static int targetFPS = 90; //Visual FPS
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

    public static void render(World world) {
        Renderer.renderBegin();

        Renderer.renderWorld(world);
        Renderer.renderer.text.DrawText("FPS: " + rCore.GetFPS(), 0,0, 30, Color.RED);
        Renderer.renderer.text.DrawText("eeee: " + Main.world.tileMap.get(new Vector2(0,3)), 0,40, 30, Color.YELLOW);
        Renderer.renderer.text.DrawText("Mouse Position: " + Input.getMousePosition().x + "," + Input.getMousePosition().y, 0, 80, 30, Color.GREEN);

        Rectangle rectangle = new Rectangle(Input.getMousePosition().x, Input.getMousePosition().y, 25,25);
        for (Vector2 vector2 : Main.world.tileMap.keySet()) {
            Tile tile = Main.world.tileMap.get(vector2);
            if (renderer.shapes.CheckCollisionRecs(tile.getRect(), rectangle)) {
                Renderer.renderer.shapes.DrawRectangle((int) rectangle.x, (int) rectangle.y, (int) rectangle.width, (int) rectangle.height, Color.RED);
                break;
            } else {
                Renderer.renderer.shapes.DrawRectangle((int) rectangle.x, (int) rectangle.y, (int) rectangle.width, (int) rectangle.height, Color.BLUE);
            }
        }


        //The above line makes a rectangle follow your cursor, cool stuff.

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
