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
import org.goose.level.Level;
import org.goose.level.World;
import org.goose.objects.Entity;
import org.goose.objects.Tile;

import java.io.IOException;

public class Renderer {

    public static Raylib renderer = new Raylib();
    public static boolean drawFPS = false;
    public static Camera2D camera = new Camera2D();
    public static int targetFPS = 90; //Visual FPS
    public static int targetTPS = 256;

    private static int windowWidth = 0; //gets set at load time
    private static int windowHeight = 0;

    public static void init() {
        rCore.SetConfigFlags(Config.ConfigFlag.FLAG_WINDOW_MAXIMIZED);
        renderer.core.InitWindow(windowWidth,windowHeight, "Platformer");
        windowWidth = rCore.GetScreenWidth();
        windowHeight = rCore.GetScreenHeight();
        renderer.core.SetWindowSize(windowWidth,windowHeight);
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

    public static void renderWorld(Level level) throws IOException {
        //draw sky first
        renderer.core.ClearBackground(Color.GRAY);
        level.render(1);

    }

    public static void render(Level world) throws IOException {
        Renderer.renderBegin();

        Renderer.renderWorld(world);

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
