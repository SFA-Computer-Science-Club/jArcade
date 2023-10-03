package org.goose.core;

import com.raylib.java.Config;
import com.raylib.java.Raylib;
import com.raylib.java.core.rCore;
import com.raylib.java.core.Color;
import com.raylib.java.core.input.Keyboard;
import com.raylib.java.core.rcamera.Camera2D;
import com.raylib.java.textures.Image;
import org.goose.level.Level;
import org.goose.objects.DirtBlock;
import org.goose.objects.GrassBlock;

import java.io.IOException;

public class Renderer {

    public static Raylib renderer = new Raylib();
    public static boolean drawFPS = false;
    public static Camera2D camera = new Camera2D();
    public static int targetFPS = 90; //Visual FPS
    public static int targetTPS = 256;

    private static int windowWidth = 0; //gets set at load time
    private static int windowHeight = 0;

    /**
     * Initialize window creation and texture loading, must be called prior to the game loop
     */
    public static void init() {
        rCore.SetConfigFlags(Config.ConfigFlag.FLAG_WINDOW_MAXIMIZED);
        renderer.core.InitWindow(windowWidth,windowHeight, "Platformer");
        windowWidth = rCore.GetScreenWidth();
        windowHeight = rCore.GetScreenHeight();
        renderer.core.SetWindowSize(windowWidth,windowHeight);
        renderer.core.SetTargetFPS(targetFPS);
        renderer.core.SetExitKey(Keyboard.KEY_DELETE);

        DirtBlock.initTexture();
        GrassBlock.initTexture();

        Image image = TextureLoader.loadImage("textures/dirt_block.png");
        Renderer.renderer.core.SetWindowIcon(image);
        Time.setLastTime(Time.now());
    }

    /**
     * Checking if any exit key was pressed, if it was, then return false
     * @return
     */
    public static boolean shouldClose() {
        return renderer.core.WindowShouldClose();
    }

    /**
     * Draws FPS at 0,0
     */
    public static void renderFPS() {
        renderer.text.DrawFPS(0,0);
    }

    /**
     * Sets up the OpenGL context to begin rendering, must be called before any draw calls
     */
    public static void renderBegin() {
        renderer.core.BeginDrawing();
        renderer.core.ClearBackground(Color.WHITE);
        renderer.core.BeginMode2D(camera);
    }

    /**
     * This method stops the rendering pipeline, call it after you stop rendering everything
     */
    public static void renderEnd() {
        renderer.core.EndMode2D();
        renderer.core.EndDrawing();
    }

    /**
     * Call level's render function
     */
    public static void renderWorld(Level level) throws IOException {
        level.render(1);
    }

    /**
     * Main render function, contains renderbegin, renderworld, and renderend
     */
    public static void render(Level world) throws IOException {
        Renderer.renderBegin();

        Renderer.renderWorld(world);

        Renderer.renderEnd();
    }

    /**
     * Draws tile at position with a given color
     * @param positionX
     * @param positionY
     * @param size
     * @param color
     */
    public static void drawTile(int positionX, int positionY, int size, Color color) {
        renderer.shapes.DrawRectangle(positionX, positionY, size, size, color);
    }

    /**
     * Returns width of game window
     * @return
     */
    public static int getWindowWidth() {
        return windowWidth;
    }

    /**
     * Sets the game window width
     * @param windowWidth
     */
    public static void setWindowWidth(int windowWidth) {
        Renderer.windowWidth = windowWidth;
    }

    /**
     * Returns the game window height
     * @return
     */
    public static int getWindowHeight() {
        return windowHeight;
    }

    /**
     * Sets the game window height
     * @param windowHeight
     */
    public static void setWindowHeight(int windowHeight) {
        Renderer.windowHeight = windowHeight;
    }
}
