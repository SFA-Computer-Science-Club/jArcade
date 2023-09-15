package org.goose.core;

import com.raylib.java.core.input.Keyboard;
import com.raylib.java.raymath.Vector2;
import com.raylib.java.core.rCore;
import com.studiohartman.jamepad.ControllerManager;

import java.util.ArrayList;

public class Input {
    //input class used to help simplify user input
    //public static

    /**
     * Will be cleared after the first update of a game loop, only runs once
     */
    public static ArrayList<Integer> pressedKeys = new ArrayList<>();
    public static ArrayList<Integer> heldKeys = new ArrayList<>();
    public static ArrayList<Integer> renderPressedKeys = new ArrayList<>();
    public static ArrayList<Integer> charPressedKeys = new ArrayList<>();
    public static ControllerManager controllerManager = new ControllerManager();

    /**
     * Initalize input devices, gamepads, joysticks
     */
    public static void init() {
        Input.controllerManager.initSDLGamepad();
    }

    /**
     * Returns true if key was pressed, doesn't check if key is held
     * @param key
     * @return
     */
    public static boolean isKeyPressed(int key) {
        return Renderer.renderer.core.IsKeyPressed(key);
    }


    /**
     * Returns a vector2 of the current mouse position
     * @return
     */
    public static Vector2 getMousePosition() {
        return rCore.GetMousePosition();
    }

    /**
     * Registers input for the game engine, returns nothing, resets the key arrays every render cycle
     */
    public static void registerInput() {
        pressedKeys.clear();
        heldKeys.clear();
        renderPressedKeys.clear();
        charPressedKeys.clear();
        //called before each frame to get the input
        charPressedKeys.add(Renderer.renderer.core.GetCharPressed());
        for (int i = 31; i < 337; i++) {
            if (isKeyDown(i)) {
                heldKeys.add(i);
            }
            if (isKeyPressed(i)) {
                pressedKeys.add(i);
                renderPressedKeys.add(i);
            }
        }
        Input.controllerManager.update();
    }

    /**
     * Returns is a certain key is held down, false if not held down
     * @param key
     * @return
     */
    public static boolean isKeyDown(int key) {
        return !Renderer.renderer.core.IsKeyUp(key);
    }

    /**
     * Check if the left-mouse key was clicked
     * @return
     */
    public static boolean isLeftMouseClicked() {
        return rCore.IsMouseButtonDown(0);
    }
}
