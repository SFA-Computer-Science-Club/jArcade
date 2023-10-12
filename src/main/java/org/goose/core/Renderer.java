package org.goose.core;

import com.raylib.java.Config;
import com.raylib.java.Raylib;
import com.raylib.java.core.rCore;
import com.raylib.java.core.Color;
import com.raylib.java.core.input.Keyboard;
import com.raylib.java.core.rcamera.Camera2D;
import com.raylib.java.textures.Image;
import org.goose.core.event.events.core.*;
import org.goose.core.input.InputListener;
import org.goose.objects.DirtBlock;
import org.goose.objects.GrassBlock;
import org.goose.objects.SFACube;

public class Renderer {

    public static Raylib renderer = new Raylib();
    public static boolean drawFPS = false;
    public static Camera2D camera = new Camera2D();
    public static int targetFPS = 90; //Visual FPS
    public static int targetTPS = 256;

    private static int windowWidth = 0; //gets set at load time
    private static int windowHeight = 0;

    private static double accumulator = 0.0;
    private static double lastUpdateTime;

    /**
     * Initialize window creation and texture loading, must be called prior to the game loop
     */
    public static void init() {
        InitializationEvent event = new InitializationEvent();//Automatically called
        InputListener inputListener = new InputListener();
        rCore.SetConfigFlags(Config.ConfigFlag.FLAG_WINDOW_MAXIMIZED);
        renderer.core.InitWindow(windowWidth,windowHeight, "Platformer");
        windowWidth = rCore.GetScreenWidth();
        windowHeight = rCore.GetScreenHeight();
        renderer.core.SetWindowSize(windowWidth,windowHeight);
        renderer.core.SetTargetFPS(targetFPS);
        renderer.core.SetExitKey(Keyboard.KEY_DELETE);
        renderer.audio.InitAudioDevice();

        DirtBlock.initTexture();
        GrassBlock.initTexture();
        SFACube.initTexture();

        Image image = TextureLoader.loadImage("textures/dirt_block.png");
        Renderer.renderer.core.SetWindowIcon(image);
        Time.setLastTime(Time.now());
        lastUpdateTime = Time.now();
    }
    public static void close() {
        CloseEvent event = new CloseEvent();
        event.dispatch();
        Renderer.renderer.core.CloseWindow();
        renderEnd();
    }

    /**
     * Dispatches the render begin event, fires after the screen is set up to draw
     */
    public static void startFrame() {
        renderBegin();
        RenderBeginEvent event = new RenderBeginEvent(); //called before each from begin
        event.dispatch();

        Input.registerInput(); //Gather input for processing in tick

        double deltaTime = (Time.now()-lastUpdateTime);
        lastUpdateTime += deltaTime;
        accumulator += deltaTime;
    }

    /**
     * Dispatches the RenderEndEvent, useful to cleanup any needed code upon rendering end
     */
    public static void endFrame() {
        RenderEndEvent event = new RenderEndEvent();
        event.dispatch();
        renderEnd();
    }

    /**
     * Dispatches the RenderDrawEvent, which should cause everything to be rendered
     */
    public static void drawFrame() {
        RenderDrawEvent event = new RenderDrawEvent();
        event.dispatch();
    }

    public static void tick() {
        TickEvent tickEvent = new TickEvent();
        tickEvent.dispatch();
        //Physics.tick(targetTPS); //Calculate physics, movement, AI etc
        accumulator -= (1000d/targetTPS);
        Input.pressedKeys.clear();
    }

    /**
     * Checking if any exit key was pressed, if it was, then return false
     * @return
     */
    public static boolean shouldClose() {
        return renderer.core.WindowShouldClose();
    }

    /**
     * Sets up the OpenGL context to begin rendering, must be called before any draw calls
     */
    public static void renderBegin() {
        renderer.core.BeginDrawing();
        renderer.core.ClearBackground(Color.BLACK);
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

    public static boolean canTick() {
            return (accumulator > (1000d/targetTPS));
    }
}
